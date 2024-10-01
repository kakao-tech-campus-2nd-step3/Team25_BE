package com.team25.backend.dto.request;

public record ManagerWorkingHourCreateRequest (
    String day,
    String startTime,
    String endTime
) {
}
