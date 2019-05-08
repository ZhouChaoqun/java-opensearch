package com.quancheng.manto.job;

import com.quancheng.manto.biz.OnlineResBiz;
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
@JobHander(value = "FullSyncOnlineResJob")
public class FullSyncOnlineResJob extends IJobHandler {

    private static final QcLog LOGGER = LogUtil.getLogger(FullSyncOnlineResJob.class);

    @Autowired
    private OnlineResBiz onlineResBiz;

    /**
     * 每天凌晨3点全量更新线上餐厅
     */
    @Override
    public ReturnT<String> execute(String... params) {
        LOGGER.warn("线上餐厅全量同步到搜索job执行");
        onlineResBiz.syncOnlineRes(null);
        return ReturnT.SUCCESS;
    }

}
