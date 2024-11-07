package com.team25.backend.domain.reservation.enumdomain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PatientGender {
    @JsonProperty("여성")
    FEMALE,
    @JsonProperty("남성")
    MALE,
    @JsonProperty("기타")
    OTHER;
}