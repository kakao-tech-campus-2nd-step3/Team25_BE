package com.team25.backend.service;

import com.team25.backend.dto.response.TokenResponse;
import com.team25.backend.dto.response.UserResponse;
import com.team25.backend.entity.Refresh;
import com.team25.backend.jwt.JWTUtil;
import com.team25.backend.repository.RefreshRepository;
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
        Long accessTokenExpiry = 600000L;  // 10분
        Long refreshTokenExpiry = 86400000L;  // 24시간

        String access = jwtUtil.createJwt("access", userDto.uuid(), accessTokenExpiry );
        String refresh = jwtUtil.createJwt("refresh", userDto.uuid(), refreshTokenExpiry);
        addRefresh(userDto.uuid(), refresh, 86400000L);

        return new TokenResponse(access, accessTokenExpiry, refresh, refreshTokenExpiry);
    }

    public void addRefresh(String uuid, String refreshToken, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        Refresh refresh = new Refresh(uuid, refreshToken, date.toString());
        refreshRepository.save(refresh);
    }
}
