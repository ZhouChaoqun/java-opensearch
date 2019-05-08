package com.quancheng.manto.job;

import com.quancheng.manto.dao.RestaurantMapper;
import com.quancheng.manto.enums.RestaurantTypeEnum;
import com.quancheng.starter.log.LogUtil;
import com.quancheng.starter.log.QcLog;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zz 2018/12/12
 */

@Component
@JobHander(value = "SyncDeletedSpecialResJob")
public class SyncDeletedSpecialResJob extends IJobHandler {

    private static final QcLog LOGGER = LogUtil.getLogger(SyncDeletedSpecialResJob.class);

    @Autowired
    private RestaurantMapper restaurantMapper;

    /**
     * 定时任务，每天凌晨0点，同步特许已上线，搜索里依然存在的特许餐厅
     */
    @Override
    public ReturnT<String> execute(String... params) {

        List<String> needToDeleteResIds;
        try {
            needToDeleteResIds = restaurantMapper.specialResIdsNeedToDelete();
        } catch (Exception e) {
            LOGGER.error("查询需要删除的特许餐厅id异常", e);
            return ReturnT.FAIL;
        }

        if (CollectionUtils.isEmpty(needToDeleteResIds)) {
            return ReturnT.SUCCESS;
        }

        restaurantMapper.delete(needToDeleteResIds, RestaurantTypeEnum.SPECIAL.getCode());

        return ReturnT.SUCCESS;
    }
}
