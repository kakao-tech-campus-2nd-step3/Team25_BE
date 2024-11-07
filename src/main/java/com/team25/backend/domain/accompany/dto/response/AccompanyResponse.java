package com.team25.backend.domain.accompany.dto.response;

import com.team25.backend.domain.accompany.enumdomain.AccompanyStatus;
import java.io.Serializable;
import java.time.LocalDateTime;

public record AccompanyResponse(
    AccompanyStatus status,
    LocalDateTime statusDate,
    String statusDescribe) implements
    Serializable {

}