package com.quancheng.manto.utils;

import com.quancheng.manto.common.SearchFunction;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

/**
 * @author zz 2018/11/14
 */
public class SearchParamUtil {

    public static String getDistanceFilter(String longitude, String latitude, Double radius) {

        if (StringUtils.isEmpty(latitude) || StringUtils.isEmpty(longitude)
                || radius == null || radius == 0) {
            return null;
        }

        return MessageFormat.format(SearchFunction.DISTANCE_FILTER, longitude, latitude, radius);
    }

    public static String getDistanceSort(String longitude, String latitude) {

        if (StringUtils.isEmpty(latitude) || StringUtils.isEmpty(longitude)) {
            return null;
        }

        return MessageFormat.format(SearchFunction.DISTANCE_SORT, longitude, latitude);
    }
}
