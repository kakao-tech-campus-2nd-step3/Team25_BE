package com.team25.backend.dto.response;

import com.team25.backend.annotation.ValidArrivalLocation;
import com.team25.backend.annotation.ValidDepartureLocation;
import com.team25.backend.annotation.ValidPrice;
import com.team25.backend.annotation.ValidServiceType;
import com.team25.backend.annotation.ValidTransportation;
import com.team25.backend.enumdomain.ServiceType;
import com.team25.backend.enumdomain.Transportation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import org.springframework.validation.annotation.Validated;

@Validated
public record ReservationResponse(
    String departureLocation,
    String arrivalLocation,
    LocalDateTime reservationDateTime,
    ServiceType serviceType,
    Transportation transportation,
    int price) {

}