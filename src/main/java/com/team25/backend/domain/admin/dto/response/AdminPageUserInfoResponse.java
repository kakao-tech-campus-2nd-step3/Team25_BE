package com.team25.backend.domain.admin.dto.response;

public record AdminPageUserInfoResponse(Long id,
                                        String username,
                                        String role,
                                        String description) {
}
