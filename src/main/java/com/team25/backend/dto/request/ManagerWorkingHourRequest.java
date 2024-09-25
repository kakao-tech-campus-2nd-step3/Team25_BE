package com.team25.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerWorkingHourRequest {
    private String day;
    private String startTime;
    private String endTime;
}
