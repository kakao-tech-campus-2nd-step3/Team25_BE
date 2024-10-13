package com.team25.backend.controller;

import com.team25.backend.dto.request.LoginRequest;
import com.team25.backend.dto.response.ApiResponse;
import com.team25.backend.dto.response.TokenResponse;
import com.team25.backend.dto.response.UserResponse;
import com.team25.backend.service.JWTService;
import com.team25.backend.service.KakaoOAuth2Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final KakaoOAuth2Service kakaoOAuth2Service;
    private final JWTService jwtService;

    public LoginController(KakaoOAuth2Service kakaoOAuth2Service, JWTService jwtService) {
        this.kakaoOAuth2Service = kakaoOAuth2Service;
        this.jwtService = jwtService;
    }

    @PostMapping("/auth/kakao/login")
    public ResponseEntity<ApiResponse<TokenResponse>> loginWithKakao(@RequestBody LoginRequest loginRequest){
        UserResponse userInfo = kakaoOAuth2Service.processKakaoLogin(loginRequest);
        TokenResponse tokenResponse = jwtService.generateJwtToken(userInfo);
        return ResponseEntity.ok(new ApiResponse<>(true,"로그인을 성공했습니다.",tokenResponse));
    }
}