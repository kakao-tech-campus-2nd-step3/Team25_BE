package com.team25.backend.enumdomain;

public enum Transportation {
    WALK("도보"), TAXI("택시");

    private final String krName;

    Transportation(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }
}
