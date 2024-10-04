package com.team25.backend.enumdomain;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    HOLD("보류"),
    CONFIRMED("확정"),
    CANCEL("취소");

    private final String krName;

    ReservationStatus(String krName) {
        this.krName = krName;
    }

}
