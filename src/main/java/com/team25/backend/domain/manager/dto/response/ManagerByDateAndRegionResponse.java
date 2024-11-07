package com.team25.backend.domain.manager.dto.response;

import com.team25.backend.domain.manager.entity.Manager;

public record ManagerByDateAndRegionResponse(
    Long managerId,
    String name,
    String profileImage,
    String career,
    String comment,
    String gender
) {
    public static ManagerByDateAndRegionResponse fromEntity(Manager manager) {
        return new ManagerByDateAndRegionResponse(
            manager.getId(),
            manager.getManagerName(),
            manager.getProfileImage(),
            manager.getCareer(),
            manager.getComment(),
            manager.getGender()
        );
    }
}
