package com.team25.backend.domain.report.enumdomain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TimeEnum {
    MORNIG("아침"),
    LUNCH("점심"),
    DINNER("저녁"),
    NIGHT("취침전");

    private final String krName;
}
