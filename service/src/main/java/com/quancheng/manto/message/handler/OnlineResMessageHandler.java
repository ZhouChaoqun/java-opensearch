package com.quancheng.manto.message.handler;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.quancheng.manto.biz.OnlineResBiz;
import com.quancheng.manto.common.Const;
import com.quancheng.manto.dao.RestaurantMapper;
import com.quancheng.manto.enums.RestaurantTypeEnum;
import com.quancheng.manto.message.MessageHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author zz 2018/12/27
 */
@Component
public class OnlineResMessageHandler extends BaseHandler implements MessageHandler {

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private OnlineResBiz onlineResBiz;

    @Override
    public Action process(String opt, JSONObject data, String tableName) {

        String resId;

        switch (tableName) {
            case Const.API_RESTAURANT_TABLE_NAME:
                resId = getValueByKey(data, "id");
                break;
            case Const.API_YUDING_TABLE_NAME:
            case Const.API_WAIMAI_RESTAURANT_TABLE_NAME:
            case Const.RESTAURANT_CLIENT_TABLE_NAME:
            case Const.CLIENT_BLACKLIST_TABLE_NAME:
                resId = getValueByKey(data, "restaurant_id");
                break;
            case Const.SETTLEMENT_TERM_TABLE_NAME:
                resId = getValueByKey(data, "shop_id");
                break;
            case Const.SETTLEMENT_BALANCE_ACCOUNT_TABLE_NAME:
                resId = getValueByKey(data, "business_id");
                break;
            default:
                return Action.CommitMessage;
        }

        if (StringUtils.isEmpty(resId)) {
            return Action.CommitMessage;
        }

        if (Const.API_RESTAURANT_TABLE_NAME.equals(tableName)) {
            String deletedTime = getValueByKey(data, "deleted_at");
            if ((Const.OPT_UPDATE.equals(opt)
                    && !StringUtils.isEmpty(deletedTime))
                    || Const.OPT_DELETE.equals(opt)) {
                String existResId = restaurantMapper.existSourceOnlineRes(resId);
                if (StringUtils.isEmpty(existResId)) {
                    restaurantMapper.delete(Collections.singletonList(resId), RestaurantTypeEnum.ONLINE.getCode());
                    return Action.CommitMessage;
                }
            }
        }

        onlineResBiz.syncOnlineRes(Collections.singletonList(resId));

        return Action.CommitMessage;
    }
}
