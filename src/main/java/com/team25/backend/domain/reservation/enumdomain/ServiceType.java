package com.team25.backend.domain.reservation.enumdomain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ServiceType {
    // 보호자대행: 수면내시경, 수술 등 보호자가 필요한 경우
    @JsonProperty("보호자대행")
    GUARDIAN_PROXY,

    // 정기동행: 방사선치료, 투석, 재활치료
    @JsonProperty("정기동행")
    REGULAR_ACCOMPANIMENT,

    // 진료동행: 원내 이동진료 돌봄
    @JsonProperty("진료동행")
    CLINIC_ESCORT,

    // 입퇴원동행: 입원, 퇴원, 전원
    @JsonProperty("입퇴원동행")
    ADMISSION_DISCHARGE_SUPPORT,

    // 전문동행: 정신과진료, 화상치료, 안과시술 등 전문적인 지식이 필요한 경우
    @JsonProperty("전문동행")
    SPECIALIZED_ACCOMPANIMENT;
}
