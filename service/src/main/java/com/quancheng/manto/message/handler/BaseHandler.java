package com.quancheng.manto.message.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zz 2018/12/27
 */
class BaseHandler {

    /**
     * 根据key来获取对应的值
     *
     * @param data
     * @param key
     * @return
     */
    String getValueByKey(JSONObject data, String key) {

        if (StringUtils.isEmpty(key)) {
            return "";
        }

        String lowerCaseKey = key.toLowerCase();

        if (data.getJSONArray(lowerCaseKey) == null) {
            return "";
        }
        JSONArray jsonArray = data.getJSONArray(lowerCaseKey);
        int index = 0;
        if (jsonArray.size() > 1) {
            index = 1;
        }
        return jsonArray.getString(index);
    }

}
