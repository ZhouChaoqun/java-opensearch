package com.quancheng.manto.job;

import com.quancheng.manto.dao.RestaurantMapper;
import com.quancheng.starter.log.LogUtil;
import com.quancheng.starter.log.QcLog;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * @author zz 2018/12/10
 */
@Component
@JobHander(value = "UnsignedRestaurantJob")
public class UnsignedRestaurantUpdateJob extends IJobHandler {

    private static final QcLog LOGGER = LogUtil.getLogger(UnsignedRestaurantUpdateJob.class);

    @Autowired
    private RestaurantMapper restaurantMapper;

    /**
     * 定时任务，每天凌晨1点刷新前一天当面付餐厅更改信息
     */
    @Override
    public ReturnT<String> execute(String... params) {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        Date startTime = c.getTime();

        try {
            restaurantMapper.syncUnsignedResByTime(startTime);
        } catch (Exception e) {
            LOGGER.error("job每天同步前一天当面付餐厅信息变动，数据库异常", e);
            return ReturnT.FAIL;
        }

        return ReturnT.SUCCESS;
    }

}
