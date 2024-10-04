package com.team25.backend.enumdomain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Transportation {
    WALK("도보"),
    TAXI("택시"),
    PUBLIC_TRANSPORTATION("대중교통");

    private final String krName;
}
