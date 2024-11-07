package com.team25.backend.domain.report.enumdomain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum MedicineTime {
    @JsonProperty("식후 30분")
    AFTER_MEAL(null),
    @JsonProperty("식전 30분")
    BEFORE_MEAL(null),
    @JsonProperty("식간")
    IN_MEAL(null),
    @JsonProperty("일정한 시간 간격")
    INTERVAL(null),
    @JsonProperty("특정 시간")
    AT_TIME(null);

    @Setter
    private String times;

}