package com.team25.backend.enumdomain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum TimeInterval {
    AFTER_MEAL("식후 30분", null),
    BEFORE_MEAL("식전 30분", null),
    IN_MEAL("식간", null),
    INTERVAL("일정한 시간 간격", null),
    AT_TIME("특정 시간", null);

    private final String koreanName;
    @Setter
    private String times;

}