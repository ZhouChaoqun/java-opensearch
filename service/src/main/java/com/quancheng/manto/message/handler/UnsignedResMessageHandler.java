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
public class UnsignedResMessageHandler extends BaseHandler implements MessageHandler {

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Override
    public Action process(String opt, JSONObject data, String tableName) {
        String resId;
        if (Const.UNSIGNED_RESTAURANT_TABLE_NAME.equals(tableName)) {
            resId = getValueByKey(data, Const.COLUMN_ID);
        } else {
            resId = getValueByKey(data, Const.COLUMN_RESTAURANT_ID);
        }
        if (StringUtils.isEmpty(resId)) {
            return Action.CommitMessage;
        }

        if (Const.UNSIGNED_RESTAURANT_TABLE_NAME.equals(tableName)
                && Const.OPT_DELETE.equals(opt)) {
            restaurantMapper.delete(Collections.singletonList(resId), RestaurantTypeEnum.UNSIGNED.getCode());
            return Action.CommitMessage;
        }
        restaurantMapper.syncUnsignedResByResIds(Collections.singletonList(resId));

        return Action.CommitMessage;
    }

}
