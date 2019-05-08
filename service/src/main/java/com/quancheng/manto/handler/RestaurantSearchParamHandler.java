package com.quancheng.manto.handler;

import com.aliyun.opensearch.sdk.generated.search.Aggregate;
import com.aliyun.opensearch.sdk.generated.search.SearchParams;
import com.quancheng.manto.common.Const;
import com.quancheng.manto.common.RestaurantConst;
import com.quancheng.manto.common.SearchFunction;
import com.quancheng.manto.enums.QueryRelationEnum;
import com.quancheng.manto.enums.SearchParamJoinerEnum;
import com.quancheng.manto.indexFieldMaps.RestaurantIndexFieldMap;
import com.quancheng.manto.model.restaurantmodel.RestaurantSearchRequest;
import com.quancheng.manto.utils.SearchParamUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author zz 2018/12/19
 */

@Component
public class RestaurantSearchParamHandler {

    private static final String BLACKLIST_CLIENT_FIELD_NAME = "blacklistClient";

    private static final int FALSE_INT_VALUE = 0;

    private static final int TRUE_INT_VALUE = 1;

    @Autowired
    private SearchParamHandler searchParamHandler;

    public void fillQueryFilterAndAgg(RestaurantSearchRequest request, SearchParams searchParams) {
        //搜索某企业开放餐厅，漏掉黑名单过滤条件，则默认加上
        String openClient = request.getOpenClient();
        if (!StringUtils.isEmpty(openClient)
                && StringUtils.isEmpty(request.getBlacklistClient())) {
            request.setBlacklistClient(openClient);
            if (request.getNotRelationCols() == null) {
                request.setNotRelationCols(new ArrayList<>());
            }
            request.getNotRelationCols().add(BLACKLIST_CLIENT_FIELD_NAME);
        }

        Map<String, String> queryFilterMap = searchParamHandler.getQueryFilterMap(request,
                RestaurantIndexFieldMap.queryMap, RestaurantIndexFieldMap.filterMap, RestaurantIndexFieldMap.rangeFilterMap);

        String query = queryFilterMap.get(Const.QUERY);
        //设置查询条件
        if (!StringUtils.isEmpty(query)) {
            searchParams.setQuery(query);
        }

        String filter = queryFilterMap.get(Const.FILTER);
        //设置距离过滤条件
        String distanceFilter = SearchParamUtil
                .getDistanceFilter(request.getLongitude(), request.getLatitude(), request.getRadius());
        if (distanceFilter != null) {
            filter = StringUtils.isEmpty(filter) ? distanceFilter : filter
                    + QueryRelationEnum.AND.getValue() + distanceFilter;
        }
        filter = fillTakeawayRangeFilter(filter, request);
        if (!StringUtils.isEmpty(filter)) {
            searchParams.setFilter(filter);
        }

        if (request.getAggCategory() != null
                && request.getAggCategory()
                && CollectionUtils.isEmpty(request.getCategory())) {
            fillAgg(searchParams, RestaurantConst.AGG_KEY_CATEGORY, Const.AGG_FUNC_COUNT);
        }
    }

    private void fillAgg(SearchParams searchParams, String groupKey, String aggFunc) {

        if (searchParams == null || StringUtils.isEmpty(groupKey) || StringUtils.isEmpty(aggFunc)) {
            return;
        }

        Aggregate agg = new Aggregate();

        agg.setGroupKey(groupKey);
        agg.setAggFun(aggFunc);

        searchParams.addToAggregates(agg);

    }

    /**
     * 设置外卖配送范围过滤条件
     */
    private String fillTakeawayRangeFilter(String filter, RestaurantSearchRequest request) {

        if (request.getTakeawayRange() == null
                || !request.getTakeawayRange()
                || StringUtils.isEmpty(request.getLongitude())
                || StringUtils.isEmpty(request.getLatitude())) {
            return filter;
        }

        String shippingDisFilter = MessageFormat.format(SearchFunction.DISTANCE_FILTER,
                request.getLongitude(), request.getLatitude(), SearchFunction.SHIPPING_DIS_INDEX);
        String inPolygonFilter = MessageFormat.format(SearchFunction.IN_POLYGON,
                request.getLongitude(), request.getLatitude());
        String havePolygonTrue = SearchFunction.HAVE_POLYGON_INDEX
                + SearchParamJoinerEnum.EQUAL.getValue() + TRUE_INT_VALUE;
        String havePolygonFalse = SearchFunction.HAVE_POLYGON_INDEX
                + SearchParamJoinerEnum.EQUAL.getValue() + FALSE_INT_VALUE;

        String takeawayRangeFilter = Const.LEFT_PARENTHESIS + Const.LEFT_PARENTHESIS + havePolygonFalse
                + QueryRelationEnum.AND.getValue()
                + shippingDisFilter + Const.RIGHT_PARENTHESIS
                + QueryRelationEnum.OR.getValue()
                + Const.LEFT_PARENTHESIS + havePolygonTrue + QueryRelationEnum.AND.getValue()
                + inPolygonFilter + Const.RIGHT_PARENTHESIS + Const.RIGHT_PARENTHESIS;

        if (StringUtils.isEmpty(filter)) {
            return takeawayRangeFilter;
        }

        return filter + QueryRelationEnum.AND.getValue() + takeawayRangeFilter;
    }

}
