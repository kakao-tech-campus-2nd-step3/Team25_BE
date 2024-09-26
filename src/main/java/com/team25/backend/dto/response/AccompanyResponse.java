package com.team25.backend.dto.response;

import com.team25.backend.enumdomain.AccompanyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.hibernate.validator.constraints.Range;


public record AccompanyResponse(
    AccompanyStatus accompanyStatus,
    Double latitude,
    Double longitude,
    LocalDateTime time,
    String detail) implements
    Serializable {

}