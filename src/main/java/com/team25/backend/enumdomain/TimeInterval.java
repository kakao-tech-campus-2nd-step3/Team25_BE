package com.team25.backend.enumdomain;

import lombok.Getter;

@Getter
public enum TimeInterval {
    THIRTY_MINUTE("30분"),
    ONE_HOUR("1시간");

    private final String koreanName;

    TimeInterval(String koreanName) {
        this.koreanName = koreanName;
    }
}
