package com.team25.backend.domain.reservation.dto.request;

import com.team25.backend.domain.reservation.annotation.ValidCancelReason;
import com.team25.backend.domain.reservation.enumdomain.CancelReason;
import jakarta.validation.constraints.NotBlank;

public record CancelRequest(
    @ValidCancelReason CancelReason cancelReason,
    @NotBlank(message = "취소 상세 이유는 필수 입력값입니다.") String cancelDetail) {

}