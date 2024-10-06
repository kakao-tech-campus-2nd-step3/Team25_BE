package com.team25.backend.enumdomain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ReservationStatus {
    @JsonProperty("보류")
    HOLD,

    @JsonProperty("확정")
    CONFIRMED,

    @JsonProperty("취소")
    CANCEL;
}