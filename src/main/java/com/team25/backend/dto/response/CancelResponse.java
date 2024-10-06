package com.team25.backend.dto.response;

import com.team25.backend.annotation.ValidCancelReason;
import jakarta.validation.constraints.NotBlank;

public record CancelResponse(
    String cancelReason,
    String cancelDetail) {

}