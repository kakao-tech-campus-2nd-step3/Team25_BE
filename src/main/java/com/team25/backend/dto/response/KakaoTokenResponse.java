package com.team25.backend.dto.response;

public record KakaoTokenResponse(String token_type,
                                 String access_token,
                                 String id_token,
                                 int expires_in,
                                 String refresh_token,
                                 int refresh_token_expires_in) {
}
