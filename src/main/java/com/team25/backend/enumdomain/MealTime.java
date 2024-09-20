package com.team25.backend.enumdomain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MealTime {
    BEFORE_MEAL("식전"),
    AFTER_MEAL("식후");

    private final String krName;
}
