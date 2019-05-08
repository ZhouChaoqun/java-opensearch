package com.quancheng.manto.message.handler;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.quancheng.manto.common.Const;
import com.quancheng.manto.dao.RestaurantMapper;
import com.quancheng.manto.enums.RestaurantTypeEnum;
import com.quancheng.manto.message.MessageHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author zz 2018/12/11
 */

@Component
public class SpecialResMessageHandler extends BaseHandler implements MessageHandler {

    @Autowired
    private RestaurantMapper restaurantMapper;

    private static final String SPECIAL_RESTAURANT_ONLINE_STATUS = "online";

    private static final String COLUMN_ONLINE_STATUS = "online_status";

    @Override
    public Action process(String opt, JSONObject data, String tableName) {

        if (!Const.RESTAURANT_INFO_TABLE_NAME.equals(tableName)) {
            return Action.CommitMessage;
        }
        String resId = getValueByKey(data, Const.COLUMN_ID);
        if (StringUtils.isEmpty(resId)) {
            return Action.CommitMessage;
        }

        if (Const.OPT_DELETE.equals(opt)
                || (Const.OPT_UPDATE.equals(opt)
                && SPECIAL_RESTAURANT_ONLINE_STATUS.equals(getValueByKey(data, COLUMN_ONLINE_STATUS)))) {
            restaurantMapper.delete(Collections.singletonList(resId), RestaurantTypeEnum.SPECIAL.getCode());
            return Action.CommitMessage;
        }
        restaurantMapper.syncSpecialResByResIds(Collections.singletonList(resId));

        return Action.CommitMessage;
    }


}
