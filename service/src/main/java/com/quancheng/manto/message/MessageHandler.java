package com.quancheng.manto.message;

import com.aliyun.openservices.ons.api.Action;
import com.alibaba.fastjson.JSONObject;

/**
 * @author zz 2018/12/27
 */
public interface MessageHandler {

    Action process(String opt, JSONObject data, String tableName);

}
