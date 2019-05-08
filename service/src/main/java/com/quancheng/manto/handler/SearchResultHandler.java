package com.quancheng.manto.handler;

import com.alibaba.fastjson.JSON;
import com.aliyun.opensearch.sdk.dependencies.org.json.JSONArray;
import com.aliyun.opensearch.sdk.dependencies.org.json.JSONObject;
import com.quancheng.manto.common.Const;
import com.quancheng.manto.common.SearchResultNodeName;
import com.quancheng.manto.model.basemodel.CountAgg;
import com.quancheng.starter.log.LogUtil;
import com.quancheng.starter.log.QcLog;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zz 2018/12/20
 */

@Component
public class SearchResultHandler {

    private static final QcLog LOGGER = LogUtil.getLogger(SearchResultHandler.class);

    private static final String CATEGORY_ALL_VALUE = "全部菜系";

    public List<CountAgg> getCountAggList(JSONObject result, String aggKey) {
        List<CountAgg> countAggs = new ArrayList<>();

        long total = result.getLong(SearchResultNodeName.TOTAL);
        CountAgg agg = new CountAgg();
        agg.setTitle(CATEGORY_ALL_VALUE + "(" + total +")");
        agg.setValue(CATEGORY_ALL_VALUE);
        agg.setCount(total + "");
        countAggs.add(agg);

        try {
            JSONArray facets = result.getJSONArray(SearchResultNodeName.FACET);
            if (facets == null || facets.length() == 0) {
                return countAggs;
            }
            for (int i = 0; i < facets.length(); i++) {
                JSONObject facet = (JSONObject) facets.get(i);
                String key = (String) facet.get(SearchResultNodeName.KEY);
                if (!aggKey.equalsIgnoreCase(key)) {
                    continue;
                }

                JSONArray items = facet.getJSONArray(SearchResultNodeName.ITEMS);
                for (int j = 0; j < items.length(); j++) {
                    JSONObject item = (JSONObject) items.get(j);
                    CountAgg countAgg = JSON.parseObject(item.toString(), CountAgg.class);
                    countAgg.setTitle(countAgg.getValue()
                            + Const.LEFT_PARENTHESIS + countAgg.getCount() + Const.RIGHT_PARENTHESIS);

                    countAggs.add(countAgg);
                }
                break;
            }
        } catch (Exception e) {
            LOGGER.error("转换查询结果异常 ", e);
            return countAggs;
        }

        sort(countAggs);
        return countAggs;
    }

    private void sort(List<CountAgg> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.sort((c1, c2) -> {
            Long count1;
            Long count2;
            try {
                count1 = Long.valueOf(c1.getCount());
                count2 = Long.valueOf(c2.getCount());
            } catch (Exception e) {
                return 0;
            }

            if (count1 > count2) {
                return -1;
            }

            if (count1 < count2) {
                return 1;
            }

            return 0;
        });
    }
}
