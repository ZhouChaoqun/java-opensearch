package com.quancheng.manto.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zz 2018/12/13
 */
public enum RestaurantSortIndexEnum {

    SORT_BY_DISTANCE("distance", "distance"),
    SORT_BY_AVG("avg", "avg"),
    SORT_BY_RATING("rating", "rating");

    private String index;

    private String field;

    RestaurantSortIndexEnum(String index, String field) {
            this.index = index;
            this.field = field;
        }

    public static String getIndexByField(String field) {

        if (StringUtils.isEmpty(field)) {
            return null;
        }

        for (RestaurantSortIndexEnum sortIndexEnum : values()) {

            if (sortIndexEnum.getField().equals(field)) {
                return sortIndexEnum.getIndex();
            }
        }

        return null;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

}
