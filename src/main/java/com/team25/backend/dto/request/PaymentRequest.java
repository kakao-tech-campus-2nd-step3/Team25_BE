package com.team25.backend.dto.request;

public record PaymentRequest(
        int amount,
        String goodsName,
        String cardQuota,
        boolean useShopInterest
) {}
