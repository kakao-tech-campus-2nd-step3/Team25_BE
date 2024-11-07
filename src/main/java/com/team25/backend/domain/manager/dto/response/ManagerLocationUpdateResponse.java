package com.team25.backend.domain.manager.dto.response;

import com.team25.backend.domain.manager.entity.Manager;

public record ManagerLocationUpdateResponse(String workingRegion) {
    public static ManagerLocationUpdateResponse fromEntity(Manager manager) {
        return new ManagerLocationUpdateResponse(manager.getWorkingRegion());
    }
}
