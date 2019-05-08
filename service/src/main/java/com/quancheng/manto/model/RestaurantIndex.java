package com.quancheng.manto.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author zz 2018/11/14
 */
public class RestaurantIndex {

    private String id;

    @JSONField(name = "restaurant_id")
    private String restaurantId;

    @JSONField(name = "amap_id")
    private String amapId;

    private String name;

    private String address;

    private String province;

    private String city;

    private String district;

    @JSONField(name = "contact_phone")
    private String contactPhone;

    private Double avg;

    private String category;

    @JSONField(name = "category_all")
    private String categoryAll;

    @JSONField(name = "indoorurls")
    private String indoorUrls;

    @JSONField(name = "outdoorurl")
    private String outdoorUrl;

    private String type;

    private String status;

    private Double rating;

    private Double latitude;

    private Double longitude;

    private Double distance;

    @JSONField(name = "meal_type")
    private String mealType;

    @JSONField(name = "have_box")
    private String haveBox;

    @JSONField(name = "opening_time")
    private String openingTime;

    @JSONField(name = "invoice_title")
    private String invoiceTitle;

    @JSONField(name = "tax_identification_number")
    private String taxIdentificationNumber;

    @JSONField(name = "food_safety_archives")
    private String foodSafetyArchives;

    @JSONField(name = "have_invoice")
    private String haveInvoice;

    @JSONField(name = "gonghai_id")
    private String gonghaiId;

    @JSONField(name = "online_restaurant_id")
    private String onlineRestaurantId;

    @JSONField(name = "disable_mark")
    private String disableMark;

    @JSONField(name = "open_client")
    private String openClient;

    @JSONField(name = "blacklist_client")
    private String blacklistClient;

    @JSONField(name = "gmt_created")
    private Long gmtCreated;

    @JSONField(name = "gmt_modified")
    private Long gmtModified;

    @JSONField(name = "business_district")
    private String businessDistrict;

    @JSONField(name = "merchant_id")
    private String merchantId;

    @JSONField(name = "merchant_name")
    private String merchantName;

    private String description;

    private Boolean einvoice;

    @JSONField(name = "online_time")
    private Long onlineTime;

    @JSONField(name = "create_time")
    private Long createTime;

    @JSONField(name = "hit_num")
    private Integer hitNum;

    @JSONField(name = "support_reservation")
    private Boolean supportReservation;

    @JSONField(name = "support_takeaway")
    private Boolean supportTakeaway;

    @JSONField(name = "advance_hour")
    private Integer advanceHour;

    @JSONField(name = "advance_time")
    private Integer advanceTime;

    @JSONField(name = "need_advance")
    private Boolean needAdvance;

    @JSONField(name = "box_num")
    private Integer boxNum;

    @JSONField(name = "box_min_consume")
    private String boxMinConsume;

    @JSONField(name = "delivery_desc")
    private String deliveryDesc;

    @JSONField(name = "max_items")
    private Integer maxItems;

    @JSONField(name = "max_type")
    private String maxType;

    @JSONField(name = "cancel_rule")
    private String cancelRule;

    @JSONField(name = "shipping_dis")
    private Double shippingDis;

    @JSONField(name = "shipping_fee")
    private Double shippingFee;

    @JSONField(name = "shipping_fee_min")
    private Double shippingFeeMin;

    @JSONField(name = "packing_box_fee")
    private Double packingBoxFee;

    @JSONField(name = "settlement_owner")
    private String settlementOwner;

    @JSONField(name = "settlement_type")
    private String settlementType;

    @JSONField(name = "rebate_id")
    private String rebateId;

    @JSONField(name = "rebate_status")
    private String rebateStatus;

    @JSONField(name = "rebate_type")
    private String rebateType;

    @JSONField(name = "rebate_biz_type")
    private String rebateBizType;

    @JSONField(name = "rebate_percent")
    private Double rebatePercent;

    @JSONField(name = "rebate_settlement_type")
    private String rebateSettlementType;

    @JSONField(name = "rebate_rule")
    private String rebateRule;

    @JSONField(name = "effective_time")
    private Long effectiveTime;

    @JSONField(name = "balance_acount_id")
    private String balanceAcountId;

    @JSONField(name = "total_amount")
    private Double totalAmount;

    @JSONField(name = "available_amount")
    private Double availableAmount;

    @JSONField(name = "frozen_amount")
    private Double frozenAmount;

    @JSONField(name = "tx_type")
    private String txType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getAmapId() {
        return amapId;
    }

    public void setAmapId(String amapId) {
        this.amapId = amapId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryAll() {
        return categoryAll;
    }

    public void setCategoryAll(String categoryAll) {
        this.categoryAll = categoryAll;
    }

    public String getIndoorUrls() {
        return indoorUrls;
    }

    public void setIndoorUrls(String indoorUrls) {
        this.indoorUrls = indoorUrls;
    }

    public String getOutdoorUrl() {
        return outdoorUrl;
    }

    public void setOutdoorUrl(String outdoorUrl) {
        this.outdoorUrl = outdoorUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getHaveBox() {
        return haveBox;
    }

    public void setHaveBox(String haveBox) {
        this.haveBox = haveBox;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public String getFoodSafetyArchives() {
        return foodSafetyArchives;
    }

    public void setFoodSafetyArchives(String foodSafetyArchives) {
        this.foodSafetyArchives = foodSafetyArchives;
    }

    public String getHaveInvoice() {
        return haveInvoice;
    }

    public void setHaveInvoice(String haveInvoice) {
        this.haveInvoice = haveInvoice;
    }

    public String getGonghaiId() {
        return gonghaiId;
    }

    public void setGonghaiId(String gonghaiId) {
        this.gonghaiId = gonghaiId;
    }

    public String getOnlineRestaurantId() {
        return onlineRestaurantId;
    }

    public void setOnlineRestaurantId(String onlineRestaurantId) {
        this.onlineRestaurantId = onlineRestaurantId;
    }

    public String getDisableMark() {
        return disableMark;
    }

    public void setDisableMark(String disableMark) {
        this.disableMark = disableMark;
    }

    public String getOpenClient() {
        return openClient;
    }

    public void setOpenClient(String openClient) {
        this.openClient = openClient;
    }

    public String getBlacklistClient() {
        return blacklistClient;
    }

    public void setBlacklistClient(String blacklistClient) {
        this.blacklistClient = blacklistClient;
    }

    public Long getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Long gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getBusinessDistrict() {
        return businessDistrict;
    }

    public void setBusinessDistrict(String businessDistrict) {
        this.businessDistrict = businessDistrict;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEinvoice() {
        return einvoice;
    }

    public void setEinvoice(Boolean einvoice) {
        this.einvoice = einvoice;
    }

    public Long getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Long onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getHitNum() {
        return hitNum;
    }

    public void setHitNum(Integer hitNum) {
        this.hitNum = hitNum;
    }

    public Boolean getSupportReservation() {
        return supportReservation;
    }

    public void setSupportReservation(Boolean supportReservation) {
        this.supportReservation = supportReservation;
    }

    public Boolean getSupportTakeaway() {
        return supportTakeaway;
    }

    public void setSupportTakeaway(Boolean supportTakeaway) {
        this.supportTakeaway = supportTakeaway;
    }

    public Integer getAdvanceHour() {
        return advanceHour;
    }

    public void setAdvanceHour(Integer advanceHour) {
        this.advanceHour = advanceHour;
    }

    public Integer getAdvanceTime() {
        return advanceTime;
    }

    public void setAdvanceTime(Integer advanceTime) {
        this.advanceTime = advanceTime;
    }

    public Boolean getNeedAdvance() {
        return needAdvance;
    }

    public void setNeedAdvance(Boolean needAdvance) {
        this.needAdvance = needAdvance;
    }

    public Integer getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(Integer boxNum) {
        this.boxNum = boxNum;
    }

    public String getBoxMinConsume() {
        return boxMinConsume;
    }

    public void setBoxMinConsume(String boxMinConsume) {
        this.boxMinConsume = boxMinConsume;
    }

    public String getDeliveryDesc() {
        return deliveryDesc;
    }

    public void setDeliveryDesc(String deliveryDesc) {
        this.deliveryDesc = deliveryDesc;
    }

    public Integer getMaxItems() {
        return maxItems;
    }

    public void setMaxItems(Integer maxItems) {
        this.maxItems = maxItems;
    }

    public String getMaxType() {
        return maxType;
    }

    public void setMaxType(String maxType) {
        this.maxType = maxType;
    }

    public String getCancelRule() {
        return cancelRule;
    }

    public void setCancelRule(String cancelRule) {
        this.cancelRule = cancelRule;
    }

    public Double getShippingDis() {
        return shippingDis;
    }

    public void setShippingDis(Double shippingDis) {
        this.shippingDis = shippingDis;
    }

    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Double getShippingFeeMin() {
        return shippingFeeMin;
    }

    public void setShippingFeeMin(Double shippingFeeMin) {
        this.shippingFeeMin = shippingFeeMin;
    }

    public Double getPackingBoxFee() {
        return packingBoxFee;
    }

    public void setPackingBoxFee(Double packingBoxFee) {
        this.packingBoxFee = packingBoxFee;
    }

    public String getSettlementOwner() {
        return settlementOwner;
    }

    public void setSettlementOwner(String settlementOwner) {
        this.settlementOwner = settlementOwner;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    public String getRebateId() {
        return rebateId;
    }

    public void setRebateId(String rebateId) {
        this.rebateId = rebateId;
    }

    public String getRebateStatus() {
        return rebateStatus;
    }

    public void setRebateStatus(String rebateStatus) {
        this.rebateStatus = rebateStatus;
    }

    public String getRebateType() {
        return rebateType;
    }

    public void setRebateType(String rebateType) {
        this.rebateType = rebateType;
    }

    public String getRebateBizType() {
        return rebateBizType;
    }

    public void setRebateBizType(String rebateBizType) {
        this.rebateBizType = rebateBizType;
    }

    public Double getRebatePercent() {
        return rebatePercent;
    }

    public void setRebatePercent(Double rebatePercent) {
        this.rebatePercent = rebatePercent;
    }

    public String getRebateSettlementType() {
        return rebateSettlementType;
    }

    public void setRebateSettlementType(String rebateSettlementType) {
        this.rebateSettlementType = rebateSettlementType;
    }

    public String getRebateRule() {
        return rebateRule;
    }

    public void setRebateRule(String rebateRule) {
        this.rebateRule = rebateRule;
    }

    public Long getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Long effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getBalanceAcountId() {
        return balanceAcountId;
    }

    public void setBalanceAcountId(String balanceAcountId) {
        this.balanceAcountId = balanceAcountId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(Double availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Double getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(Double frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public String getTxType() {
        return txType;
    }

    public void setTxType(String txType) {
        this.txType = txType;
    }
}
