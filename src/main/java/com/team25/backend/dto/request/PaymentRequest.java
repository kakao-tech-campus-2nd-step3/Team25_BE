package com.team25.backend.dto.request;

public record PaymentRequest(
        String orderId,
        int amount,
        String goodsName,
        String cardQuota,
        boolean useShopInterest
) {}
