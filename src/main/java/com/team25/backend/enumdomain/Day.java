package com.team25.backend.enumdomain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Day {
    @JsonProperty("월")
    MONDAY,
    @JsonProperty("화")
    TUESDAY,
    @JsonProperty("수")
    WEDNESDAY,
    @JsonProperty("목")
    THURSDAY,
    @JsonProperty("금")
    FRIDAY,
    @JsonProperty("토")
    SATURDAY,
    @JsonProperty("일")
    SUNDAY;


    private static final Map<String, Day> ENGLISH_TO_DAY_MAP = Arrays.stream(Day.values())
        .collect(Collectors.toMap(Enum::name, Function.identity()));

    public static Day fromEnglishName(String englishName) {
        Day day = ENGLISH_TO_DAY_MAP.get(englishName.toUpperCase());
        if (day == null) {
            throw new IllegalArgumentException("Invalid day name: " + englishName);
        }
        return day;
    }
}