package com.team25.backend.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team25.backend.dto.request.LogoutRequest;
import com.team25.backend.dto.response.ApiResponse;
import com.team25.backend.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class CustomLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final ObjectMapper objectMapper;

    public CustomLogoutFilter(JWTUtil jwtUtil, RefreshRepository refreshRepository, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/auth\\/logout$")) {

            filterChain.doFilter(request, response);
            return;
        }
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {

            filterChain.doFilter(request, response);
            return;
        }


        LogoutRequest logoutRequest = objectMapper.readValue(request.getInputStream(), LogoutRequest.class);
        String refresh = logoutRequest.refreshToken();


        if (refresh == null) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Refresh 토큰이 제공되지 않았습니다.");
            return;
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Refresh 토큰이 만료되었습니다.");
            return;
        } catch (MalformedJwtException e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "잘못된 형식의 Refresh 토큰입니다.");
            return;
        } catch (SignatureException e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "잘못된 서명의 Refresh 토큰입니다.");
            return;
        } catch (UnsupportedJwtException e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "지원되지 않는 Refresh 토큰입니다.");
            return;
        } catch (IllegalArgumentException e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Refresh 토큰이 비어있습니다.");
            return;
        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Refresh 토큰 처리 중 오류가 발생했습니다.");
            return;
        }


        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "해당 JWT 토큰이 Refresh 토큰이 아닙니다.");
            return;
        }


        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "해당 Refresh 토큰이 존재하지 않습니다.");
            return;
        }

        refreshRepository.deleteByRefresh(refresh);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<?> apiResponse = new ApiResponse<>(true, "로그아웃을 성공했습니다.", null);
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);

        response.getWriter().write(jsonResponse);
    }

    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<?> apiResponse = new ApiResponse<>(false, message, null);
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);

        response.getWriter().write(jsonResponse);
    }
}

