package com.team25.backend.dto.request;

public record ManagerCreateRequest(
    String name,
    String profileImage,
    String career,
    String comment,
    String certificateImage,
    String gender
) {
}
