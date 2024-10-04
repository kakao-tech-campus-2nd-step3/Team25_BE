package com.team25.backend.enumdomain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Day {
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
    SUNDAY("일");

    private final String krName;

    private static final Map<String, Day> KOREAN_TO_DAY_MAP = Arrays.stream(Day.values())
        .collect(Collectors.toMap(Day::getKrName, Function.identity()));

    private static final Map<String, Day> ENGLISH_TO_DAY_MAP = Arrays.stream(Day.values())
        .collect(Collectors.toMap(Enum::name, Function.identity()));

    public static Day fromKoreanName(String koreanName) {
        Day day = KOREAN_TO_DAY_MAP.get(koreanName);
        if (day == null) {
            throw new IllegalArgumentException("Invalid day name: " + koreanName);
        }
        return day;
    }

    public static Day fromEnglishName(String englishName) {
        Day day = ENGLISH_TO_DAY_MAP.get(englishName.toUpperCase());
        if (day == null) {
            throw new IllegalArgumentException("Invalid day name: " + englishName);
        }
        return day;
    }
}