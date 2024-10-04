package com.team25.backend.enumdomain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PatientGender {
    FEMALE("여성"),
    MALE("남성"),
    OTHER("기타");

    private final String krName;
}
