package com.team25.backend.domain.auth.dto.response;

public record TokenResponse (
        String accessToken,
        Long expiresIn,
        String refreshToken,
        Long refreshTokenExpiresIn
) {
}
