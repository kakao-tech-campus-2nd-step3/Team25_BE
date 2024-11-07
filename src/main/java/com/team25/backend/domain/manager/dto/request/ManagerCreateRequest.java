package com.team25.backend.domain.manager.dto.request;

public record ManagerCreateRequest(
    String name,
    String profileImage,
    String career,
    String comment,
    String certificateImage,
    String gender
) {
}
