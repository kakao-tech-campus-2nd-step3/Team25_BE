package com.team25.backend.dto.response;

import com.team25.backend.entity.Manager;
import lombok.*;

@Getter
@Builder
public class ManagerByDateAndRegionResponse {
    private Long managerId;
    private String name;
    private String profileImage;
    private String career;
    private String comment;

    public static ManagerByDateAndRegionResponse fromEntity(Manager manager) {
        return ManagerByDateAndRegionResponse.builder()
            .managerId(manager.getId())
            .name(manager.getManagerName())
            .profileImage(manager.getProfileImage())
            .career(manager.getCareer())
            .comment(manager.getComment())
            .build();
    }
}
