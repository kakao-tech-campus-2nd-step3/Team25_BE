package com.team25.backend.dto.request;

public record PaymentCancelRequest(
        String tid,
        String reason,
        String orderId
)
{}
