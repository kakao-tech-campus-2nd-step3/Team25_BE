package com.team25.backend.dto.response;

import com.team25.backend.entity.Manager;
import com.team25.backend.entity.WorkingHour;

import java.util.List;
import java.util.stream.Collectors;

public record ManagerProfileResponse(
    String name,
    String profileImage,
    String career,
    String comment,
    String workingRegion,
    List<WorkingHourResponse> workingHour
) {
    public static ManagerProfileResponse fromEntity(Manager manager) {
        return new ManagerProfileResponse(
            manager.getManagerName(),
            manager.getProfileImage(),
            manager.getCareer(),
            manager.getComment(),
            manager.getWorkingRegion(),
            manager.getWorkingHours().stream()
                .map(WorkingHourResponse::fromEntity)
                .collect(Collectors.toList())
        );
    }

    public record WorkingHourResponse(
        String day,
        String startTime,
        String endTime
    ) {
        public static WorkingHourResponse fromEntity(WorkingHour workingHour) {
            return new WorkingHourResponse(
                workingHour.getDay().getKrName(),
                workingHour.getStartTime(),
                workingHour.getEndTime()
            );
        }
    }
}
