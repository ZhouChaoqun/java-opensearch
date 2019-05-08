package com.quancheng.manto.enums;

/**
 * @author zz 2018/11/14
 */
public enum QueryRelationEnum {

    AND(" AND "),
    OR(" OR "),
    VERTICAL_LINE_OR("\"|\""),
    ANDNOT(" ANDNOT "),
    RANK(" RANK "),
    LEFT_PARENTHESIS("("),
    RIGHT_PARENTHESIS(")"),
    RANGE_MORE(">="),
    RANGE_LESS("<=");

    private String value;

    QueryRelationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
