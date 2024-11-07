package com.team25.backend.domain.auth.controller;

import com.team25.backend.domain.auth.dto.request.ReissueRequest;
import com.team25.backend.global.util.ApiResponse;
import com.team25.backend.domain.auth.dto.response.TokenResponse;
import com.team25.backend.domain.user.dto.response.UserResponse;
import com.team25.backend.domain.auth.security.JWTUtil;
import com.team25.backend.domain.auth.repository.RefreshRepository;
import com.team25.backend.domain.auth.service.JWTService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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

    @PostMapping("/auth/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> reissue(@RequestBody ReissueRequest reissueRequest){
        String refresh = reissueRequest.refreshToken();

        // 아무것도 안보냈을 때
        if (refresh == null) {
            log.info("Refresh 토큰이 제공되지 않았습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<> (false, "Refresh 토큰이 제공되지 않았습니다.", null));
        }

        // 만료된 refresh 토큰
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            log.info("Refresh 토큰이 만료되었습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<> (false, "Refresh 토큰이 만료되었습니다.", null));
        } catch (MalformedJwtException e) {
            log.info("잘못된 형식의 Refresh 토큰입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<> (false, "잘못된 형식의 Refresh 토큰입니다.", null));
        } catch (Exception e){
            log.info(e.getMessage());

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
