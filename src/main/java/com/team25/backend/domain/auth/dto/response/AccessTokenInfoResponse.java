package com.team25.backend.domain.auth.dto.response;

public record AccessTokenInfoResponse(Long id,
                                      Integer expires_in,
                                      Integer app_id) {
}
