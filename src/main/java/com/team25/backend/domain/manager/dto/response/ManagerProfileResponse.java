package com.team25.backend.domain.manager.dto.response;

import com.team25.backend.domain.manager.entity.Manager;
import com.team25.backend.domain.manager.entity.WorkingHour;

public record ManagerProfileResponse(
    String name,
    String profileImage,
    String career,
    String comment,
    String workingRegion,
    String gender,
    WorkingHourResponse workingHour
) {
    public static ManagerProfileResponse fromEntity(Manager manager) {
        return new ManagerProfileResponse(
            manager.getManagerName(),
            manager.getProfileImage(),
            manager.getCareer(),
            manager.getComment(),
            manager.getWorkingRegion(),
            manager.getGender(),
            WorkingHourResponse.fromEntity(manager.getWorkingHour())
        );
    }

    public record WorkingHourResponse(
        String monStartTime, String monEndTime,
        String tueStartTime, String tueEndTime,
        String wedStartTime, String wedEndTime,
        String thuStartTime, String thuEndTime,
        String friStartTime, String friEndTime,
        String satStartTime, String satEndTime,
        String sunStartTime, String sunEndTime
    ) {
        public static WorkingHourResponse fromEntity(WorkingHour workingHour) {
            return new WorkingHourResponse(
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
}
