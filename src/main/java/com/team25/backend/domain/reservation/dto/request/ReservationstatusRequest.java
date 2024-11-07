package com.team25.backend.domain.reservation.dto.request;

import com.team25.backend.domain.reservation.enumdomain.ReservationStatus;

public record ReservationstatusRequest(
    ReservationStatus reservationStatus
) {

}
