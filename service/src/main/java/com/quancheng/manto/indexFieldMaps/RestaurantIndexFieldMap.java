package com.quancheng.manto.indexFieldMaps;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zz 2018/12/18
 */
public class RestaurantIndexFieldMap {

    public static final Map<String, String> queryMap;

    public static final Map<String, String> filterMap;

    public static final Map<String, String> rangeFilterMap;

    static {
        queryMap = new HashMap<>();
        queryMap.put("id", "id");
        queryMap.put("search", "search");
        queryMap.put("restaurantId", "restaurant_id");
        queryMap.put("amapId", "amap_id");
        queryMap.put("name", "name");
        queryMap.put("address", "address");
        queryMap.put("province", "province");
        queryMap.put("city", "city");
        queryMap.put("district", "district");
        queryMap.put("contactPhone", "contact_phone");
        queryMap.put("category", "category");
        queryMap.put("categoryAll", "category_all");
        queryMap.put("type", "type");
        queryMap.put("status", "status");
        queryMap.put("mealType", "meal_type");
        queryMap.put("haveBox", "have_box");
        queryMap.put("openingTime", "opening_time");
        queryMap.put("invoiceTitle", "invoice_title");
        queryMap.put("taxIdentificationNumber", "tax_identification_number");
        queryMap.put("foodSafetyArchives", "food_safety_archives");
        queryMap.put("haveInvoice", "have_invoice");
        queryMap.put("gonghaiId", "gonghai_id");
        queryMap.put("onlineRestaurantId", "online_restaurant_id");
        queryMap.put("disableMark", "disable_mark");
        queryMap.put("openClient", "open_client");
        queryMap.put("blacklistClient", "blacklist_client");
        queryMap.put("merchantId", "merchant_id");
        queryMap.put("merchantName", "merchant_name");
        queryMap.put("businessDistrict", "business_district");
        queryMap.put("settlementType", "settlement_type");
        queryMap.put("rebateSettlementType", "rebate_settlement_type");
        queryMap.put("supportReservation", "support_reservation");
        queryMap.put("supportTakeaway", "support_takeaway");
        queryMap.put("einvoice", "einvoice");
    }

    static {
        filterMap = new HashMap<>();
        filterMap.put("id", "id");
        filterMap.put("restaurantId", "restaurant_id");
        filterMap.put("amapId", "amap_id");
        filterMap.put("type", "type");
        filterMap.put("status", "status");
        filterMap.put("haveBox", "have_box");
        filterMap.put("openingTime", "opening_time");
        filterMap.put("haveInvoice", "have_invoice");
        filterMap.put("gonghaiId", "gonghai_id");
        filterMap.put("onlineRestaurantId", "online_restaurant_id");
        filterMap.put("openClient", "open_client");
        filterMap.put("blacklistClient", "blacklist_client");
        filterMap.put("merchantId", "merchant_id");
    }

    static {
        rangeFilterMap = new HashMap<>();
        rangeFilterMap.put("avgRange", "avg");
        rangeFilterMap.put("ratingRange", "rating");
    }
}
