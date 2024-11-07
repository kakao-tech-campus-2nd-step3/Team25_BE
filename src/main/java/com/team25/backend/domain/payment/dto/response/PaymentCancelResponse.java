package com.team25.backend.domain.payment.dto.response;

public record PaymentCancelResponse(
        String resultCode,
        String resultMsg,
        String tid,
        String orderId,
        String ediDate,
        String status,
        String paidAt,
        int amount,
        int balanceAmt,
        String goodsName,
        boolean useEscrow,
        String currency,
        String receiptUrl
)
{}
