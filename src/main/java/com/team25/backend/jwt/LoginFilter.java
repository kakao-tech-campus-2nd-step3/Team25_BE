package com.team25.backend.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team25.backend.dto.CustomUserDetails;
import com.team25.backend.dto.response.TokenResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final AuthenticationManager authenticationManager;
    //JWTUtil 주입
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // JSON 형태의 요청에서 username, password 추출
            Map<String, String> loginRequest = objectMapper.readValue(request.getInputStream(), Map.class);

            String username = loginRequest.get("username");

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, null);

            //token에 담은 검증을 위한 AuthenticationManager로 전달
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        System.out.println("success");

        //유저 정보
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String uuid = userDetails.getUuid(); // UUID 정보 가져오기

        //토큰 생성
        String access = jwtUtil.createJwt("access", uuid, 600000L);
        log.info("access token: " + access);
        String refresh = jwtUtil.createJwt("refresh", uuid, 86400000L);

        // TokenResponse 객체 생성
        TokenResponse tokenResponse = new TokenResponse(access, refresh);

        // 응답 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 상태 코드 200 설정
        response.setStatus(HttpServletResponse.SC_OK);

        // TokenResponse를 JSON으로 변환하여 응답에 작성
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(tokenResponse);
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            log.error("Error while sending token response", e);
        }


    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
