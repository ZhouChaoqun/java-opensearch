package com.quancheng.manto.handler;

import com.quancheng.manto.common.Const;
import com.quancheng.manto.enums.QueryRelationEnum;
import com.quancheng.manto.enums.SearchParamJoinerEnum;
import com.quancheng.manto.model.basemodel.BoolValue;
import com.quancheng.starter.log.LogUtil;
import com.quancheng.starter.log.QcLog;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author zz 2018/12/18
 */
@Component
public class SearchParamHandler {

    private static final QcLog LOGGER = LogUtil.getLogger(SearchParamHandler.class);

    public Map<String, String> getQueryFilterMap(Object paramObj, Map<String, String> queryMap,
                                                 Map<String, String> filterMap, Map<String, String> rangeFilterMap) {

        Map<String, String> queryFilterMap = new HashMap<>();

        if (paramObj == null || queryMap == null || filterMap == null || rangeFilterMap == null
                || queryMap.size() == 0) {
            return queryFilterMap;
        }

        Map<String, String> paramMap;
        try {
            paramMap = getParamMap(paramObj);
        } catch (Exception e) {
            LOGGER.error("解析queryMap异常", e);
            return queryFilterMap;
        }

        List<String> emptySearchCols = getListByStr(paramMap.get(Const.REQUEST_EMPTY_SEARCH_COLS));
        List<String> notRelationCols = getListByStr(paramMap.get(Const.REQUEST_NOT_RELATION_COLS));
        List<String> orRelationCols = getListByStr(paramMap.get(Const.REQUEST_OR_RELATION_COLS));

        String emptyFilter = handleEmptySearchCols(emptySearchCols, paramMap, filterMap);
        String notFilter = handleNotRelationCols(notRelationCols, paramMap, filterMap);
        String orQuery = handleOrRelationCols(orRelationCols, paramMap, queryMap);
        String rangeFilter = handleRangeCols(paramMap, rangeFilterMap);

        String query = getQueryStr(paramMap, orQuery, queryMap);
        String filter = getFilterStr(emptyFilter, notFilter, rangeFilter);

        queryFilterMap.put(Const.QUERY, query);
        queryFilterMap.put(Const.FILTER, filter);

        return queryFilterMap;
    }

    private List<String> getListByStr(String str) {

        List<String> cols = new ArrayList<>();

        if (StringUtils.isEmpty(str)) {
            return cols;
        }

        String[] strArray = str.split(Const.VERTICAL_LINE_OR_SPLITTER);

        return new ArrayList<>(Arrays.asList(strArray));
    }

    private Map<String, String> getParamMap(Object obj) throws IllegalAccessException {

        Map<String, String> paramMap = new HashMap<>();
        if (obj == null) {
            return paramMap;
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {

            String fieldName = field.getName();
            field.setAccessible(true);
            Object fieldValue = field.get(obj);

            if (fieldValue == null) {
                continue;
            }

            fillParamMap(fieldName, fieldValue, paramMap);
        }

        return paramMap;
    }

    private void fillParamMap(String fieldName, Object fieldValue, Map<String, String> paramMap) {

        if (fieldValue instanceof String) {

            String value = (String) fieldValue;
            if (StringUtils.isEmpty(value)) {
                return;
            }

            paramMap.put(fieldName, value);
            return;
        }

        if (fieldValue instanceof BoolValue) {

            BoolValue value = (BoolValue) fieldValue;

            if (BoolValue.EMPTY.equals(value)) {
                return;
            }

            if (BoolValue.TRUE.equals(value)) {
                paramMap.put(fieldName, "1");
                return;
            }

            if (BoolValue.FALSE.equals(value)) {
                paramMap.put(fieldName, "0");
                return;
            }

            return;
        }

        if (fieldValue instanceof List) {

            String orValueStr = getOrValueStr(fieldValue);
            if (StringUtils.isEmpty(orValueStr)) {
                return;
            }

            paramMap.put(fieldName, orValueStr);
        }

    }

    private String getOrValueStr(Object fieldValue) {

        String orValueStr = "";

        List list = (List) fieldValue;
        if (CollectionUtils.isEmpty(list)) {
            return orValueStr;
        }

        StringJoiner joiner = new StringJoiner(QueryRelationEnum.VERTICAL_LINE_OR.getValue());

        for (Object obj : list) {

            String value = (String) obj;

            if (StringUtils.isEmpty(value)) {
                continue;
            }

            joiner.add(value);
        }

        return joiner.toString();

    }

    private String getQueryStr(Map<String, String> paramMap, String orRelationStr, Map<String, String> queryMap) {

        String queryStr;

        StringJoiner joiner = new StringJoiner(QueryRelationEnum.AND.getValue());
        for (String key : paramMap.keySet()) {

            String value = paramMap.get(key);
            if (StringUtils.isEmpty(value)) {
                continue;
            }

            String index = queryMap.get(key);
            if (StringUtils.isEmpty(index)) {
                continue;
            }

            joiner.add(getKVpair(index, value, SearchParamJoinerEnum.COLON));
        }

        queryStr = joiner.toString();

        if (!StringUtils.isEmpty(orRelationStr)) {
            queryStr = StringUtils
                    .isEmpty(queryStr) ? orRelationStr : queryStr + QueryRelationEnum.AND.getValue() + orRelationStr;
        }

        return queryStr;
    }

    private String getFilterStr(String emptyFilter, String notFilter, String rangeFilter) {

        StringJoiner joiner = new StringJoiner(QueryRelationEnum.AND.getValue());

        if (!StringUtils.isEmpty(emptyFilter)) {
            joiner.add(emptyFilter);
        }

        if (!StringUtils.isEmpty(notFilter)) {
            joiner.add(notFilter);
        }

        if (!StringUtils.isEmpty(rangeFilter)) {
            joiner.add(rangeFilter);
        }

        return joiner.toString();
    }

    private String handleEmptySearchCols(List<String> emptySearchCols, Map<String, String> paramMap,
                                        Map<String, String> filterMap) {

        String emptySearchStr = "";

        if (CollectionUtils.isEmpty(emptySearchCols)
                || filterMap.size() == 0) {
            return emptySearchStr;
        }

        for (String col : emptySearchCols) {

            String value = paramMap.get(col);
            paramMap.remove(col);

            List<String> valList = getListByStr(value);
            String index = filterMap.get(col);
            if (StringUtils.isEmpty(index)) {
                continue;
            }

            StringJoiner joiner = new StringJoiner(QueryRelationEnum.OR.getValue());

            for (String val : valList) {

                if (StringUtils.isEmpty(val)) {
                    continue;
                }

                joiner.add(getKVpair(index, val, SearchParamJoinerEnum.EQUAL));
            }

            joiner.add(getKVpair(index, "", SearchParamJoinerEnum.EQUAL));

            StringJoiner colJoiner = new StringJoiner(QueryRelationEnum.AND.getValue());

            if (!StringUtils.isEmpty(emptySearchStr)) {
                colJoiner.add(emptySearchStr);
            }

            colJoiner.add(QueryRelationEnum.LEFT_PARENTHESIS.getValue()
                    + joiner.toString() + QueryRelationEnum.RIGHT_PARENTHESIS.getValue());

            emptySearchStr = colJoiner.toString();
        }

        return emptySearchStr;
    }

    private String handleOrRelationCols(List<String> orRelationCols, Map<String, String> paramMap,
                                        Map<String, String> queryMap) {

        String orRelationStr = "";
        if (CollectionUtils.isEmpty(orRelationCols)
                || paramMap.size() == 0) {
            return orRelationStr;
        }

        StringJoiner joiner = new StringJoiner(QueryRelationEnum.OR.getValue());
        for (String col : orRelationCols) {

            String value = paramMap.get(col);
            paramMap.remove(col);

            if (StringUtils.isEmpty(value)) {
                continue;
            }

            String index = queryMap.get(col);
            if (StringUtils.isEmpty(index)) {
                continue;
            }

            joiner.add(getKVpair(index, value, SearchParamJoinerEnum.COLON));
        }

        return QueryRelationEnum.LEFT_PARENTHESIS.getValue() + joiner.toString()
                + QueryRelationEnum.RIGHT_PARENTHESIS.getValue();
    }

    private String handleNotRelationCols(List<String> notRelationCols, Map<String, String> paramMap,
                                         Map<String, String> filterMap) {

        String notRelationStr = "";
        if (CollectionUtils.isEmpty(notRelationCols)
                || paramMap.size() == 0
                || filterMap.size() == 0) {
            return notRelationStr;
        }

        for (String col : notRelationCols) {

            String value = paramMap.get(col);
            paramMap.remove(col);

            if (StringUtils.isEmpty(value)) {
                continue;
            }

            List<String> valList = getListByStr(value);
            String index = filterMap.get(col);
            if (StringUtils.isEmpty(index)) {
                continue;
            }

            StringJoiner joiner = new StringJoiner(QueryRelationEnum.AND.getValue());

            for (String val : valList) {

                if (StringUtils.isEmpty(val)) {
                    continue;
                }

                joiner.add(getKVpair(index, val, SearchParamJoinerEnum.NO_EQUAL));
            }

            StringJoiner colJoiner = new StringJoiner(QueryRelationEnum.AND.getValue());

            if (!StringUtils.isEmpty(notRelationStr)) {
                colJoiner.add(notRelationStr);
            }

            if (!StringUtils.isEmpty(joiner.toString())) {
                colJoiner.add(joiner.toString());
            }

            notRelationStr = colJoiner.toString();
        }

        return notRelationStr;
    }

    private String handleRangeCols(Map<String, String> paramMap, Map<String, String> rangeFilterMap) {

        String rangeFilterStr = "";

        if (paramMap.size() == 0 || rangeFilterMap.size() == 0) {
            return rangeFilterStr;
        }

        for (String key : rangeFilterMap.keySet()) {

            String value = paramMap.get(key);
            String index = rangeFilterMap.get(key);

            String rangeFilter = getRangeFilter(index, value);
            StringJoiner joiner = new StringJoiner(QueryRelationEnum.AND.getValue());

            if (!StringUtils.isEmpty(rangeFilterStr)) {
                joiner.add(rangeFilterStr);
            }

            if (!StringUtils.isEmpty(rangeFilter)) {
                joiner.add(rangeFilter);
            }

            rangeFilterStr = joiner.toString();
        }

        return rangeFilterStr;
    }

    private String getRangeFilter(String index, String str) {

        String rangeFilter = "";

        if (StringUtils.isEmpty(index) || StringUtils.isEmpty(str)) {
            return rangeFilter;
        }

        String[] strArray = str.split(Const.WAVE_NUM_SPLITTER);

        if (strArray.length > 2 || strArray.length == 0) {
            return rangeFilter;
        }

        if (strArray.length == 1) {

            if (StringUtils.isEmpty(strArray[0])) {
                return rangeFilter;
            }

            return index + QueryRelationEnum.RANGE_MORE.getValue() + strArray[0];
        }

        if (StringUtils.isEmpty(strArray[0])) {

            return index + QueryRelationEnum.RANGE_LESS.getValue() + strArray[1];
        }

        return index + QueryRelationEnum.RANGE_MORE.getValue() + strArray[0]
                + QueryRelationEnum.AND.getValue()
                + index + QueryRelationEnum.RANGE_LESS.getValue() + strArray[1];
    }

    private String getKVpair(String key, String value, SearchParamJoinerEnum joiner) {

        if (StringUtils.isEmpty(key) || joiner == null) {
            return "";
        }

        if (SearchParamJoinerEnum.COLON.equals(joiner) && StringUtils.isEmpty(value)) {
            return "";
        }

        return key + joiner.getValue() + Const.DOUBLE_QUOTE + value + Const.DOUBLE_QUOTE;

    }
}
