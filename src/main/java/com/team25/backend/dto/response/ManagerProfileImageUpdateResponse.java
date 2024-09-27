package com.team25.backend.dto.response;

import com.team25.backend.entity.Manager;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ManagerProfileImageUpdateResponse {
    private String profileImage;

    public static ManagerProfileImageUpdateResponse fromEntity(Manager manager) {
        return ManagerProfileImageUpdateResponse.builder()
            .profileImage(manager.getProfileImage())
            .build();
    }
}
