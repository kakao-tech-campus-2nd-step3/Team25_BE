package com.team25.backend.dto.response;

public class PaymentResponse {
    private String resultCode;
    private String resultMsg;
    private String tid;
    private String orderId;
    private String ediDate;
    private String status;
    private String paidAt;
    private int amount;
    private int balanceAmt;
    private String goodsName;
    private boolean useEscrow;
    private String currency;
    private String receiptUrl;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEdiDate() {
        return ediDate;
    }

    public void setEdiDate(String ediDate) {
        this.ediDate = ediDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBalanceAmt() {
        return balanceAmt;
    }

    public void setBalanceAmt(int balanceAmt) {
        this.balanceAmt = balanceAmt;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public boolean isUseEscrow() {
        return useEscrow;
    }

    public void setUseEscrow(boolean useEscrow) {
        this.useEscrow = useEscrow;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }
}
