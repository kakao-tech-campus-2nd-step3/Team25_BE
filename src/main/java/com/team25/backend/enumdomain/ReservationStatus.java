package com.team25.backend.enumdomain;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    // RESERVATIONNIL("예약없음"),
    // INRESERVATION("예약중"),
    // RESERVED("예약완료"),
    // CANCELREASERVATION("예약취소"),
    HOLD("보류"),
    CONFIRMED("확정"),
    CANCEL("취소");

    private final String krName;

    ReservationStatus(String krName) {
        this.krName = krName;
    }

}
