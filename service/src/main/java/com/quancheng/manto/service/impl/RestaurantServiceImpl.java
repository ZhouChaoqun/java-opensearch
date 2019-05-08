package com.quancheng.manto.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.opensearch.sdk.dependencies.com.google.common.collect.Lists;
import com.aliyun.opensearch.sdk.dependencies.org.json.JSONArray;
import com.aliyun.opensearch.sdk.dependencies.org.json.JSONObject;
import com.aliyun.opensearch.sdk.generated.search.*;
import com.quancheng.common.util.BeanUtils;
import com.quancheng.manto.biz.SearchBiz;
import com.quancheng.manto.common.RestaurantConst;
import com.quancheng.manto.common.SearchResultNodeName;
import com.quancheng.manto.enums.RestaurantSortIndexEnum;
import com.quancheng.manto.handler.RestaurantSearchParamHandler;
import com.quancheng.manto.handler.SearchResultHandler;
import com.quancheng.manto.model.ResponseStatus;
import com.quancheng.manto.model.RestaurantIndex;
import com.quancheng.manto.model.basemodel.CountAgg;
import com.quancheng.manto.model.basemodel.PageRequest;
import com.quancheng.manto.model.restaurantmodel.RestaurantInfo;
import com.quancheng.manto.model.restaurantmodel.RestaurantSearchRequest;
import com.quancheng.manto.model.restaurantmodel.RestaurantSearchResponse;
import com.quancheng.manto.service.RestaurantService;
import com.quancheng.manto.utils.OpenSearchUtil;
import com.quancheng.manto.utils.SearchParamUtil;
import com.quancheng.starter.log.LogUtil;
import com.quancheng.starter.log.QcLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zz 2018/11/13
 */
@Api(tags = {"搜索"})
@RestController("restaurantServiceImpl")
@RequestMapping("/restaurant")
public class RestaurantServiceImpl extends BaseService implements RestaurantService {

    private static final QcLog LOGGER = LogUtil.getLogger(RestaurantServiceImpl.class);

    @Autowired
    private SearchBiz searchBiz;

    @Autowired
    private RestaurantSearchParamHandler restaurantSearchParamHandler;

    @Autowired
    private SearchResultHandler searchResultHandler;

    @ApiOperation(value = "餐厅搜索")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @Override
    public RestaurantSearchResponse restaurantSearch(@RequestBody RestaurantSearchRequest request) {
        Config config = new Config(Lists.newArrayList(OpenSearchUtil.getResAppName()));
        //分页
        PageRequest base = initPageRequest(request.getPageRequest());

        config.setStart((base.getCurrentPage() - 1) * base.getPageSize());
        config.setHits(base.getPageSize());

        config.setSearchFormat(SearchFormat.FULLJSON);
        SearchParams searchParams = new SearchParams(config);
        restaurantSearchParamHandler.fillQueryFilterAndAgg(request, searchParams);
        // 设置sort条件
        fillSorter(searchParams, request);

        JSONObject result = searchBiz.search(searchParams);
        if (result == null) {
            LOGGER.error("调用open search查询失败");
            return buildResponse(RestaurantSearchResponse.class, ResponseStatus.ERR_SYS);
        }

        List<RestaurantInfo> resInfoList = parseSearchResult(result);
        long total = result.getLong(SearchResultNodeName.TOTAL);
        int totalPage = ((int) total + base.getPageSize() - 1) / base.getPageSize();

        //是否单独查询菜系
        List<CountAgg> categoryAggList = new ArrayList<>();
        if (request.getAggCategory() != null && request.getAggCategory()) {
            if (CollectionUtils.isEmpty(request.getCategory())) {
                categoryAggList = searchResultHandler.getCountAggList(result, RestaurantConst.AGG_KEY_CATEGORY);
            } else {
                categoryAggList = searchCategoryList(searchParams, request);
            }
        }

        RestaurantSearchResponse response = buildPagedResponse(RestaurantSearchResponse.class,
                base.getCurrentPage(), total, totalPage, resInfoList);
        response.setCategoryList(categoryAggList);

        return response;
    }

    private List<CountAgg> searchCategoryList(SearchParams searchParams, RestaurantSearchRequest request) {
        List<CountAgg> categoryList = new ArrayList<>();
        request.setCategory(null);
        restaurantSearchParamHandler.fillQueryFilterAndAgg(request, searchParams);
        JSONObject result = searchBiz.search(searchParams);
        if (result == null) {
            LOGGER.error("调用open search查询菜系统计失败");
            return categoryList;
        }

        return searchResultHandler.getCountAggList(result, RestaurantConst.AGG_KEY_CATEGORY);
    }

    private void fillSorter(SearchParams searchParams, RestaurantSearchRequest request) {
        if (StringUtils.isEmpty(request.getSortBy())) {
            return;
        }

        String index = RestaurantSortIndexEnum.getIndexByField(request.getSortBy());
        if (index == null) {
            return;
        }

        Sort sorter = new Sort();
        Order order = request.getSortAsc() == null || request.getSortAsc() ? Order.INCREASE : Order.DECREASE;
        if (RestaurantSortIndexEnum.SORT_BY_DISTANCE.getIndex().equals(index)) {
            String distanceSort = SearchParamUtil
                    .getDistanceSort(request.getLongitude(), request.getLatitude());
            if (distanceSort == null) {
                return;
            }
            sorter.addToSortFields(new SortField(distanceSort, order));
        } else {
            sorter.addToSortFields(new SortField(index, order));
        }

        searchParams.setSort(sorter);
    }

    private List<RestaurantInfo> parseSearchResult(JSONObject result) {
        List<RestaurantInfo> restaurants = new ArrayList<>();
        if (result == null) {
            return restaurants;
        }

        List<RestaurantIndex> resIndexList = new ArrayList<>();
        try {
            JSONArray items = result.getJSONArray(SearchResultNodeName.ITEMS);
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = (JSONObject) items.get(i);
                RestaurantIndex restaurantIndex = JSON.parseObject(item
                        .get(SearchResultNodeName.FIELDS).toString(), RestaurantIndex.class);
                JSONObject variableValue = (JSONObject) item.get(SearchResultNodeName.VARIABLE_VALUE);
                try {

                    JSONArray extInfo = (JSONArray) variableValue.get(SearchResultNodeName.EXT_INFO);
                    restaurantIndex.setDistance((Double) extInfo.get(0));

                } catch (Exception e) {
                    //有异常表示获取不到距离，属正常现象
                }
                resIndexList.add(restaurantIndex);
            }
        } catch (Exception e) {
            LOGGER.error("转换查询结果异常 ", e);
            return restaurants;
        }

        return resIndex2Info(resIndexList);
    }

    private List<RestaurantInfo> resIndex2Info(List<RestaurantIndex> resIndexList) {
        List<RestaurantInfo> restaurants = new ArrayList<>();
        if (CollectionUtils.isEmpty(resIndexList)) {
            return restaurants;
        }

        for (RestaurantIndex index : resIndexList) {
            RestaurantInfo info = new RestaurantInfo();
            BeanUtils.convertBean(index, info);
            String indoorUrls = index.getIndoorUrls();
            if (!StringUtils.isEmpty(indoorUrls)) {
                String[] urls = indoorUrls.split(",");
                info.setIndoorUrls(Arrays.asList(urls));
            }
            String openClient = index.getOpenClient();
            if (!StringUtils.isEmpty(openClient)) {
                String[] strList = openClient.split("\t");
                info.setOpenClient(Arrays.asList(strList));
            }
            String blacklistClient = index.getBlacklistClient();
            if (!StringUtils.isEmpty(blacklistClient)) {
                String[] strList = blacklistClient.split("\t");
                info.setBlacklistClient(Arrays.asList(strList));
            }
            String category = index.getCategory();
            if (!StringUtils.isEmpty(category)) {
                String[] categoryList = category.split("\t");
                info.setCategory(Arrays.asList(categoryList));
            }

            restaurants.add(info);
        }

        return restaurants;
    }


}
