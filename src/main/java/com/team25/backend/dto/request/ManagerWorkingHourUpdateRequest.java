package com.team25.backend.dto.request;

public record ManagerWorkingHourUpdateRequest (
    String day,
    String startTime,
    String endTime
) {
}
