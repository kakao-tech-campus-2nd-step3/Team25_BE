package com.team25.backend.dto.response;

import com.team25.backend.entity.Manager;
import com.team25.backend.entity.WorkingHour;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class ManagerProfileResponse {
    private String name;
    private String profileImage;
    private String career;
    private String comment;
    private String workingRegion;
    private List<WorkingHourResponse> workingHour;

    public static ManagerProfileResponse fromEntity(Manager manager) {
        return ManagerProfileResponse.builder()
            .name(manager.getManagerName())
            .profileImage(manager.getProfileImage())
            .career(manager.getCareer())
            .comment(manager.getComment())
            .workingRegion(manager.getWorkingRegion())
            .workingHour(manager.getWorkingHours().stream()
                .map(WorkingHourResponse::fromEntity)
                .collect(Collectors.toList()))
            .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class WorkingHourResponse {
        private String day;
        private String startTime;
        private String endTime;

        public static WorkingHourResponse fromEntity(WorkingHour workingHour) {
            return WorkingHourResponse.builder()
                .day(workingHour.getDay().getKrName())
                .startTime(workingHour.getStartTime())
                .endTime(workingHour.getEndTime())
                .build();
        }
    }
}
