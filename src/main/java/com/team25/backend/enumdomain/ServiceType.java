package com.team25.backend.enumdomain;

public enum ServiceType {
    // 동행, 진료, 응급
    ACCOMPANIMENT("동행"),
    CONSULTATION("진료"),
    EMERGENCY("응급"),
    OUTPATIENT("외래진료"),
    ENDOSCOPY("내시경검사");

    private final String krName;

    ServiceType(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
