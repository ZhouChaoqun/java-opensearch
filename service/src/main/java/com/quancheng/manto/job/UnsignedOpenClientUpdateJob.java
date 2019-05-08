package com.quancheng.manto.job;

import com.quancheng.manto.dao.OpenClientMapper;
import com.quancheng.manto.dataobject.OpenClientDO;
import com.quancheng.manto.enums.RestaurantTypeEnum;
import com.quancheng.starter.log.LogUtil;
import com.quancheng.starter.log.QcLog;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zz 2018/12/10
 */
@Component
@JobHander(value = "UnsignedOpenClientUpdateJob")
public class UnsignedOpenClientUpdateJob extends IJobHandler {

    private static final QcLog LOGGER = LogUtil.getLogger(UnsignedOpenClientUpdateJob.class);

    @Value("${unsignedOpenClientScanInterval}")
    private int timeInterval;

    @Autowired
    private OpenClientMapper openClientMapper;

    /**
     * 定时任务，每隔5分钟扫描当面付餐厅开放企业信息变动
     */
    @Override
    public ReturnT<String> execute(String... params) {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, -timeInterval);
        Date startTime = c.getTime();

        List<String> resIds;
        List<OpenClientDO> openClientList;
        try {
            resIds = openClientMapper.unsignedOpenClientResIds(startTime);
            if (CollectionUtils.isEmpty(resIds)) {
                return ReturnT.SUCCESS;
            }
            openClientList = openClientMapper.unsignedOpenClientByResId(resIds);
        } catch (Exception e) {
            LOGGER.error("job扫描当面付餐厅开放企业信息变动，数据库异常", e);
            return ReturnT.FAIL;
        }

        for (OpenClientDO openClientDO : openClientList) {
            try {
                openClientMapper.updateOpenClient(openClientDO.getResId(),
                        RestaurantTypeEnum.UNSIGNED.getCode(), openClientDO.getClientIds());
            } catch (Exception e) {
                LOGGER.error("更新当面付餐厅开放企业信息异常，resId:{}, clientIds:{}",
                        openClientDO.getResId(), openClientDO.getClientIds(), e);
            }
        }

        if (resIds.size() == openClientList.size()) {
            return ReturnT.SUCCESS;
        }

        List<String> resIdsByOpenClient = openClientList.stream().map(OpenClientDO::getResId).collect(Collectors.toList());
        resIds.removeAll(resIdsByOpenClient);
        try {
            openClientMapper.batchUpdateOpenClientEmpty(resIds, RestaurantTypeEnum.UNSIGNED.getCode());
        } catch (Exception e) {
            LOGGER.error("更新当面付餐厅开放企业为空时异常", e);
            return ReturnT.FAIL;
        }

        return ReturnT.SUCCESS;
    }
}
