package com.team25.backend.dto.response;

import com.team25.backend.entity.WorkingHour;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ManagerWorkingHourUpdateResponse {
    private String day;
    private String startTime;
    private String endTime;

    public static ManagerWorkingHourUpdateResponse fromEntity(WorkingHour workingHour) {
        return ManagerWorkingHourUpdateResponse.builder()
            .day(workingHour.getDay().getKrName())
            .startTime(workingHour.getStartTime())
            .endTime(workingHour.getEndTime())
            .build();
    }
}
