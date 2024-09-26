package com.team25.backend.dto.response;

import com.team25.backend.entity.Manager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ManagerCommentUpdateResponse {
    private String comment;

    public static ManagerCommentUpdateResponse fromEntity(Manager manager) {
        return ManagerCommentUpdateResponse.builder()
            .comment(manager.getComment())
            .build();
    }
}
