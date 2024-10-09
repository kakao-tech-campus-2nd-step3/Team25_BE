package com.team25.backend.dto.request;

import com.team25.backend.annotation.ValidCancelReason;
import com.team25.backend.enumdomain.CancelReason;
import jakarta.validation.constraints.NotBlank;

public record CancelRequest(
    @ValidCancelReason
    CancelReason cancelReason,
    @NotBlank(message = "취소 상세 이유는 필수 입력값입니다.")
    String cancelDetail) {

}