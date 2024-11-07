package com.team25.backend.domain.payment.dto.request;

public record PaymentRequest(
        int amount,
        String goodsName,
        String cardQuota,
        boolean useShopInterest,
        Long reservationId
) {}
