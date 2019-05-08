package com.quancheng.manto.enums;

/**
 * @author zz 2018/12/18
 */
public enum SearchParamJoinerEnum {

    COLON(":"),

    EQUAL("="),

    NO_EQUAL("!=");

    private String value;

    SearchParamJoinerEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
