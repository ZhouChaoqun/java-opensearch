package com.quancheng.manto.job;

import com.quancheng.manto.dao.RestaurantMapper;
import com.quancheng.starter.log.LogUtil;
import com.quancheng.starter.log.QcLog;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zz 2018/12/12
 */

@Component
@JobHander(value = "FullSyncSpecialResJob")
public class FullSyncSpecialResJob extends IJobHandler {

    private static final QcLog LOGGER = LogUtil.getLogger(FullSyncSpecialResJob.class);

    @Autowired
    private RestaurantMapper restaurantMapper;

    /**
     * 每周日凌晨2点全量更新特许餐厅
     */
    @Override
    public ReturnT<String> execute(String... params) {
        LOGGER.warn("特许餐厅全量同步到搜索job执行");

        try {
            restaurantMapper.syncSpecialResByResIds(null);
        } catch (Exception e) {
            LOGGER.error("全量同步特许餐厅数据库异常", e);
            return ReturnT.FAIL;
        }

        return ReturnT.SUCCESS;
    }

}
