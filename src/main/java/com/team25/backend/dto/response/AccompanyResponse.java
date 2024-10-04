package com.team25.backend.dto.response;

import com.team25.backend.enumdomain.AccompanyStatus;
import java.io.Serializable;
import java.time.LocalDateTime;

public record AccompanyResponse(
    AccompanyStatus status,
    Double latitude,
    Double longitude,
    LocalDateTime statusDate,
    String statusDescribe) implements
    Serializable {

}