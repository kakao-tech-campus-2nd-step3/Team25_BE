package com.team25.backend.domain.payment.dto.request;

public record PaymentCancelRequest(
        String reason,
        String orderId
)
{}
