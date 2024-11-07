package com.team25.backend.domain.accompany.enumdomain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AccompanyStatus {
    @JsonProperty("병원 이동")
    ARRIVE_AT_HOSPITAL,
    @JsonProperty("진료 접수")
    REGISTER,
    @JsonProperty("검사 및 진료")
    EXAMINATION,
    @JsonProperty("진료 예약")
    SCHEDULE_FOLLOWUP,
    @JsonProperty("수납 및 서류 발급")
    PAYMENT_AND_DOCS,
    @JsonProperty("약국 동행")
    VISIT_PHARMACY,
    @JsonProperty("귀가")
    RETURN_HOME;
}