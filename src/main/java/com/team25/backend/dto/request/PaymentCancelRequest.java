package com.team25.backend.dto.request;

public record PaymentCancelRequest(
        String reason,
        String orderId
)
{}
