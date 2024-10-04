package com.team25.backend.enumdomain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceType {
    // 보호자대행: 수면내시경, 수술 등 보호자가 필요한 경우
    GUARDIAN_PROXY("보호자대행"),
    // 정기동행: 방사선치료, 투석, 재활치료
    REGULAR_ACCOMPANIMENT("정기동행"),
    // 진료동행: 원내 이동진료 돌봄
    CLINIC_ESCORT("진료동행"),
    // 입퇴원동행: 입원, 퇴원, 전원
    ADMISSION_DISCHARGE_SUPPORT("입퇴원동행"),
    // 전문동행: 정신과진료, 화상치료, 안과시술 등 전문적인 지식이 필요한 경우
    SPECIALIZED_ACCOMPANIMENT("전문동행");

    private final String krName;

}
