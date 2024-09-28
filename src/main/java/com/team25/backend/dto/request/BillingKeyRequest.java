package com.team25.backend.dto.request;

public record BillingKeyRequest(
        String encData,
        String cardAlias
) {}
