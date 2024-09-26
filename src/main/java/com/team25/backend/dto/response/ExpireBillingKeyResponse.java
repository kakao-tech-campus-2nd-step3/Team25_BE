package com.team25.backend.dto.response;

public record ExpireBillingKeyResponse(
        String resultCode,
        String resultMsg,
        String tid,
        String orderId,
        String bid,
        String authDate
) {}
