package com.quancheng.manto.enums;

/**
 * @author zz 2018/12/10
 */
public enum RestaurantTypeEnum {

    UNSIGNED("unsigned","当面付餐厅"),
    SPECIAL("special","特许餐厅"),
    ONLINE("online","线上餐厅");

    private String code;

    private String desc;

    RestaurantTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
