package com.team25.backend.dto.response;

public record BillingKeyResponse(
        String resultCode,
        String resultMsg,
        String bid,
        String authDate,
        String cardCode,
        String cardName,
        String tid,
        String orderId
) {}
