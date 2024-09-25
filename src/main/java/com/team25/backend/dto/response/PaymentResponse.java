package com.team25.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
