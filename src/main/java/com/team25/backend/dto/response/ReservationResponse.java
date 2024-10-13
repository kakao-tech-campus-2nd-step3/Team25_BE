package com.team25.backend.dto.response;

import com.team25.backend.enumdomain.ServiceType;
import com.team25.backend.enumdomain.Transportation;
import java.time.LocalDateTime;
import org.springframework.validation.annotation.Validated;

@Validated
public record ReservationResponse(
    String departureLocation,
    String arrivalLocation,
    LocalDateTime reservationDateTime,
    ServiceType serviceType,
    Transportation transportation,
    int price
) {

}