package com.team25.backend.enumdomain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CancelReason {
    PATIENT_CANCEL("피진료자취소"),
    MANAGER_CANCEL("매니저취소"),
    CAREGIVER_CANCEL("보호자취소"),
    CHANGE_OF_MIND("단순변심"),// 단순 변심
    SECHEDULED_REBOOK("재예약예정"),// 재예약 예정
    NO_CANCEL("취소안함"),
    NO_NANGER_MATCHED("매니저매치안됨"),// 매니저 매치 안됨
    PAYMENT_FAILER("예약 결제 실패");

    private final String krName;
}
