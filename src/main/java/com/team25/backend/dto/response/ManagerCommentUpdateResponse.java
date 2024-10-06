package com.team25.backend.dto.response;

import com.team25.backend.entity.Manager;

public record ManagerCommentUpdateResponse(String comment) {
    public static ManagerCommentUpdateResponse fromEntity(Manager manager) {
        return new ManagerCommentUpdateResponse(manager.getComment());
    }
}
