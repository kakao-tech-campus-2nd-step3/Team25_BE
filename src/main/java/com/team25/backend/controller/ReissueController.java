package com.team25.backend.controller;

import com.team25.backend.dto.request.ReissueRequest;
import com.team25.backend.dto.response.ApiResponse;
import com.team25.backend.dto.response.TokenResponse;
import com.team25.backend.dto.response.UserResponse;
import com.team25.backend.jwt.JWTUtil;
import com.team25.backend.repository.RefreshRepository;
import com.team25.backend.service.JWTService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReissueController {
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final JWTService jwtService;

    public ReissueController(JWTUtil jwtUtil, RefreshRepository refreshRepository, JWTService jwtService) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<ApiResponse<TokenResponse>> reissue(HttpServletRequest request, HttpServletResponse response, @RequestBody ReissueRequest reissueRequest){
        String refresh = reissueRequest.refreshToken();

        // 아무것도 안보냈을 때
        if (refresh == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<> (false, "Refresh 토큰이 제공되지 않았습니다.", null));
        }

        // 만료된 refresh 토큰
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<> (false, "Refresh 토큰이 만료되었습니다.", null));
        } catch (MalformedJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<> (false, "잘못된 형식의 Refresh 토큰입니다.", null));
        }



        String category = jwtUtil.getCategory(refresh);
        // 종류가 리프레시 토큰이 아닌 경우
        if (!category.equals("refresh")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<> (false, "해당 JWT 토큰은 Refresh 토큰이 아닙니다.", null));
        }

        // 삭제된 리프레시 토큰
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<> (false, "해당 Refresh 토큰이 존재하지 않습니다.", null));
        }

        refreshRepository.deleteByRefresh(refresh);
        String userUUID = jwtUtil.getUuid(refresh);
        TokenResponse reissueResponse = jwtService.generateJwtToken(new UserResponse(null, userUUID,null));

        return ResponseEntity.ok(new ApiResponse<>(true, "토큰이 재발급 되었습니다.", reissueResponse));
    }
}
