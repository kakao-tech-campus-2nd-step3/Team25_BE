package com.team25.backend.dto.response;

import com.team25.backend.entity.Manager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ManagerLocationUpdateResponse {
    private String workingRegion;

    public static ManagerLocationUpdateResponse fromEntity(Manager manager) {
        return ManagerLocationUpdateResponse.builder()
            .workingRegion(manager.getWorkingRegion())
            .build();
    }
}
