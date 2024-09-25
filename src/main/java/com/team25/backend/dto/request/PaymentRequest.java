package com.team25.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private String orderId;
    private int amount;
    private String goodsName;
    private String cardQuota = "0";
    private boolean useShopInterest = false;
}
