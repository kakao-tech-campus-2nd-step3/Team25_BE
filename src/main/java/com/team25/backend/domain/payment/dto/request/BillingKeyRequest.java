package com.team25.backend.domain.payment.dto.request;

public record BillingKeyRequest(
        String encData,
        String cardAlias
) {}
