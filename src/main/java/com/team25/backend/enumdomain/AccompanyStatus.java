package com.team25.backend.enumdomain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccompanyStatus {
    ARRIVE_AT_HOSPITAL("병원 이동"),
    REGISTER("진료 접수"),
    EXAMINATION("검사 및 진료"),
    SCHEDULE_FOLLOWUP("진료 예약"),
    PAYMENT_AND_DOCS("수납 및 서류 발급"),
    VISIT_PHARMACY("약국 동행"),
    RETURN_HOME("귀가");

    private final String krName;
}
