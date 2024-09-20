package com.team25.backend.dto;

import com.team25.backend.annotation.ValidCancelReason;
import jakarta.validation.constraints.NotBlank;

public record CancelDto(
    @ValidCancelReason
    String cancelReason,
    @NotBlank(message = "취소 상세 이유는 필수 입력값입니다.")
    String cancelDetail) {

}