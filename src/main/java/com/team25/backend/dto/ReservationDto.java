package com.team25.backend.dto;

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
import org.springframework.validation.annotation.Validated;

@Validated
public record ReservationDto(
    @ValidDepartureLocation String departureLocation,
    @ValidArrivalLocation String arrivalLocation,
    @NotNull(message = "예약 일시를 입력해 주십시오.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", message = "잘못된 날짜 형식입니다. 'yyyy-MM-dd HH:mm:ss' 형식으로 입력해주세요.")
    String reservationDateTime,
    @ValidServiceType ServiceType serviceType,
    @ValidTransportation Transportation transportation,
    @Min(value = 0, message = "가격은 0 이상 입니다.") @ValidPrice String price) {

}