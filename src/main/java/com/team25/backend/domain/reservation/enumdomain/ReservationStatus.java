package com.team25.backend.domain.reservation.enumdomain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ReservationStatus {
    @JsonProperty("보류")
    HOLD,

    @JsonProperty("취소")
    CANCEL,

    @JsonProperty("확정")
    CONFIRMED,

    @JsonProperty("진행중")
    IN_PROGRESS,

    @JsonProperty("완료")
    COMPLETED
}