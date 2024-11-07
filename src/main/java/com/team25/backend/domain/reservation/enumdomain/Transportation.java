package com.team25.backend.domain.reservation.enumdomain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Transportation {
    @JsonProperty("도보")
    WALK,
    @JsonProperty("택시")
    TAXI,
    @JsonProperty("대중교통")
    PUBLIC_TRANSPORTATION;
}
