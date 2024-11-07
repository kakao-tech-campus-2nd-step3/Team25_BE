package com.team25.backend.domain.payment.dto.response;

public record PaymentResponse(
        String resultCode,
        String resultMsg,
        String tid,
        String orderId,
        String status,
        String paidAt,
        String cancelledAt,
        String payMethod,
        int amount,
        int balanceAmt,
        String goodsName,
        String receiptUrl
) {}
