package com.team25.backend.dto.response;

import com.team25.backend.entity.Manager;

public record ManagerProfileImageUpdateResponse(String profileImage) {
    public static ManagerProfileImageUpdateResponse fromEntity(Manager manager) {
        return new ManagerProfileImageUpdateResponse(manager.getProfileImage());
    }
}
