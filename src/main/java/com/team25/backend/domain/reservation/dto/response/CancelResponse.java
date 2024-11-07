package com.team25.backend.domain.reservation.dto.response;

public record CancelResponse(
    String cancelReason,
    String cancelDetail) {

}