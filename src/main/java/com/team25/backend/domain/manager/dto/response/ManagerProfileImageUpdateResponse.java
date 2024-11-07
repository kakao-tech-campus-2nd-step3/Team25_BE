package com.team25.backend.domain.manager.dto.response;

import com.team25.backend.domain.manager.entity.Manager;

public record ManagerProfileImageUpdateResponse(String profileImage) {
    public static ManagerProfileImageUpdateResponse fromEntity(Manager manager) {
        return new ManagerProfileImageUpdateResponse(manager.getProfileImage());
    }
}
