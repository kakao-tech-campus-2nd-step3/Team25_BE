package com.team25.backend.dto.request;

import com.team25.backend.enumdomain.AccompanyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.hibernate.validator.constraints.Range;

public record AccompanyRequest(
    @NotNull(message = "진행 상태틑 0자 이상이어야 합니다.") AccompanyStatus status,
    @PositiveOrZero @Range(message = "위도는 0이상 90 이하의 값입니다.", min = 0, max = 90) Double latitude,
    @PositiveOrZero @Range(message = "경도는 0이상 360이하의 값입니다.", min = 0, max = 360) Double longitude,
    @NotNull(message = "시간은 필수 입력 값입니다.") @Past LocalDateTime statusDate,
    @NotBlank(message = "리포트 상세 사항은 필수 사항입니다.") String statusDescribe) implements
    Serializable {
}