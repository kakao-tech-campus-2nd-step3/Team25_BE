package com.team25.backend.dto.response;

import com.team25.backend.entity.WorkingHour;

public record ManagerWorkingHourUpdateResponse(
    String day,
    String startTime,
    String endTime
) {
    public static ManagerWorkingHourUpdateResponse fromEntity(WorkingHour workingHour) {
        return new ManagerWorkingHourUpdateResponse(
            workingHour.getDay().getKrName(),
            workingHour.getStartTime(),
            workingHour.getEndTime()
        );
    }
}
