package com.team25.backend.domain.manager.dto.request;

public record ManagerWorkingHourUpdateRequest(
    String monStartTime,
    String monEndTime,
    String tueStartTime,
    String tueEndTime,
    String wedStartTime,
    String wedEndTime,
    String thuStartTime,
    String thuEndTime,
    String friStartTime,
    String friEndTime,
    String satStartTime,
    String satEndTime,
    String sunStartTime,
    String sunEndTime
) {
}