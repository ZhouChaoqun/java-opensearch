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
@JobHander(value = "SyncDeletedUnsignedResJob")
public class SyncDeletedUnsignedResJob extends IJobHandler {

    private static final QcLog LOGGER = LogUtil.getLogger(SyncDeletedUnsignedResJob.class);

    @Autowired
    private RestaurantMapper restaurantMapper;

    /**
     * 定时任务，每天凌晨0点，同步当面付已删除，搜索里依然存在的当面付餐厅
     */
    @Override
    public ReturnT<String> execute(String... params) {

        List<String> needToDeleteUnsignedResIds;
        try {
            needToDeleteUnsignedResIds = restaurantMapper.unsignedResIdsNeedToDelete();
        } catch (Exception e) {
            LOGGER.error("查询需要删除的当面付餐厅id异常", e);
            return ReturnT.FAIL;
        }

        if (CollectionUtils.isEmpty(needToDeleteUnsignedResIds)) {
            return ReturnT.SUCCESS;
        }

        restaurantMapper.delete(needToDeleteUnsignedResIds, RestaurantTypeEnum.UNSIGNED.getCode());

        return ReturnT.SUCCESS;
    }
}
