package com.team25.backend.domain.auth.service;

import com.team25.backend.domain.auth.repository.RefreshRepository;
import com.team25.backend.domain.auth.dto.response.TokenResponse;
import com.team25.backend.domain.user.dto.response.UserResponse;
import com.team25.backend.domain.auth.entity.Refresh;
import com.team25.backend.domain.auth.security.JWTUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    private JWTUtil jwtUtil;
    private RefreshRepository refreshRepository;

    public JWTService(JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    public TokenResponse generateJwtToken(UserResponse userDto){
        Long accessTokenExpiry = 60000L;  // 1분
        Long refreshTokenExpiry = 120000L;  // 2분 테스트용입니다!!

        String access = jwtUtil.createJwt("access", userDto.uuid(), accessTokenExpiry );
        String refresh = jwtUtil.createJwt("refresh", userDto.uuid(), refreshTokenExpiry);
        addRefresh(userDto.uuid(), refresh, 120000L);

        return new TokenResponse(access, accessTokenExpiry, refresh, refreshTokenExpiry);
    }

    public void addRefresh(String uuid, String refreshToken, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        Refresh refresh = new Refresh(uuid, refreshToken, date.toString());
        refreshRepository.save(refresh);
    }
}
