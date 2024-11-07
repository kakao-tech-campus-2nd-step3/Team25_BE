package com.team25.backend.domain.manager.dto.response;

import com.team25.backend.domain.manager.entity.WorkingHour;

public record ManagerWorkingHourUpdateResponse(
    String monStartTime, String monEndTime,
    String tueStartTime, String tueEndTime,
    String wedStartTime, String wedEndTime,
    String thuStartTime, String thuEndTime,
    String friStartTime, String friEndTime,
    String satStartTime, String satEndTime,
    String sunStartTime, String sunEndTime
) {
    public static ManagerWorkingHourUpdateResponse fromEntity(WorkingHour workingHour) {
        return new ManagerWorkingHourUpdateResponse(
            workingHour.getMonStartTime(), workingHour.getMonEndTime(),
            workingHour.getTueStartTime(), workingHour.getTueEndTime(),
            workingHour.getWedStartTime(), workingHour.getWedEndTime(),
            workingHour.getThuStartTime(), workingHour.getThuEndTime(),
            workingHour.getFriStartTime(), workingHour.getFriEndTime(),
            workingHour.getSatStartTime(), workingHour.getSatEndTime(),
            workingHour.getSunStartTime(), workingHour.getSunEndTime()
        );
    }
}
