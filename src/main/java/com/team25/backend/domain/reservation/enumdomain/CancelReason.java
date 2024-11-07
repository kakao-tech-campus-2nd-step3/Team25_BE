package com.team25.backend.domain.reservation.enumdomain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CancelReason {
    @JsonProperty("피진료자취소")
    PATIENT_CANCEL,
    @JsonProperty("매니저취소")
    MANAGER_CANCEL,
    @JsonProperty("보호자취소")
    CAREGIVER_CANCEL,
    @JsonProperty("단순변심")
    CHANGE_OF_MIND,
    @JsonProperty("재예약예정")
    SECHEDULED_REBOOK,
    @JsonProperty("취소안함")
    NO_CANCEL,
    @JsonProperty("매니저매치안됨")
    NO_NANGER_MATCHED,
    @JsonProperty("예약 결제 실패")
    PAYMENT_FAILER;
}