package com.team25.backend.dto.request;

public class PaymentRequest {
    private String orderId;
    private int amount;
    private String goodsName;
    private String cardQuota = "0";
    private boolean useShopInterest = false;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getCardQuota() {
        return cardQuota;
    }

    public void setCardQuota(String cardQuota) {
        this.cardQuota = cardQuota;
    }

    public boolean isUseShopInterest() {
        return useShopInterest;
    }

    public void setUseShopInterest(boolean useShopInterest) {
        this.useShopInterest = useShopInterest;
    }
}
