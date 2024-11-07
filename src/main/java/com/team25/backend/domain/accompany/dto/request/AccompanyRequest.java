package com.team25.backend.domain.accompany.dto.request;

import com.team25.backend.domain.accompany.enumdomain.AccompanyStatus;
import java.io.Serializable;

public record AccompanyRequest(
    AccompanyStatus status,
    Double latitude,
    Double longitude,
    String statusDate,
    String statusDescribe) implements
    Serializable {

}