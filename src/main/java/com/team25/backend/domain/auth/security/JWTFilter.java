package com.team25.backend.domain.auth.security;

import com.team25.backend.domain.auth.dto.CustomUserDetails;
import com.team25.backend.domain.user.entity.User;
import com.team25.backend.global.exception.CustomException;
import com.team25.backend.domain.user.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

import static com.team25.backend.global.exception.ErrorCode.USER_NOT_FOUND;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    public JWTFilter(JWTUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Authorization");

        if (checkHeader(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        accessToken = accessToken.split(" ")[1];

        try {
            // 토큰 유효성 검사를 포함한 예외 처리
            jwtUtil.isExpired(accessToken);
            String category = jwtUtil.getCategory(accessToken);

            if (!category.equals("access")) {
                sendErrorResponse(response, "토큰이 Access token이 아닙니다.", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String uuid = jwtUtil.getUuid(accessToken);
            User foundUser = userRepository.findByUuid(uuid)
                    .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

            String username = foundUser.getUsername();
            String role = foundUser.getRole();

            User user = new User(username, uuid, role);
            CustomUserDetails customUserDetails = new CustomUserDetails(user);

            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, "Access token이 만료되었습니다.", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (MalformedJwtException | SignatureException | IllegalArgumentException e) {
            sendErrorResponse(response, "잘못된 토큰 형식입니다.", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (CustomException e) {
            sendErrorResponse(response, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // Error 응답을 JSON 형식으로 보내기 위한 메서드
    private void sendErrorResponse(HttpServletResponse response, String message, int statusCode) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);

        PrintWriter writer = response.getWriter();
        writer.print("{\"status\": false, \"message\": \"" + message + "\", \"data\": null}");
        writer.flush();
    }


    private boolean checkHeader(String authorization) {
        return authorization == null || !authorization.startsWith("Bearer ");
    }
}