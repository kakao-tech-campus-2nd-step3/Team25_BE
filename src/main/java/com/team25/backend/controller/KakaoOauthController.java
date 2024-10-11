package com.team25.backend.controller;

import com.team25.backend.dto.response.KakaoTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;
/*
* 카카오 Access Token을 발급 받는 Controller 입니다.
*
* 실제 서비스에서는 안드로이드에서 Access Token을 백엔드로 넘겨서
* 백엔드에서 해당 토큰이 유효한 지 확인하고 jwt 토큰을 발급합니다
* 하지만 임의 터스트를 위해 카카오 서버에서 Access Token을
* 발급받는 로직을 추가했습니다..!
*
* 리팩토링을 하지 않은 이유는 실제 배포할 때는 뺄 기능이기 때문입니다!
* */
@Controller
public class KakaoOauthController {
    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.redirect-url}")
    private String redirectUri;
    @Value("${kakao.client-secret}")
    private String clientSecret;

    @GetMapping("/oauth2/authorize/kakao")
    public String redirectToKakaoAuth() {
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri;

        return "redirect:" + kakaoAuthUrl;
    }

    @GetMapping("/oauth2/callback/kakao")
    public ResponseEntity<KakaoTokenResponse> getKakaoToken(HttpServletRequest servletRequest) {
        var url = "https://kauth.kakao.com/oauth/token";

        RestTemplate restTemplate = new RestTemplate();
        String code = servletRequest.getParameter("code");

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);
        body.add("client_secret", clientSecret);
        var request = new RequestEntity<>(body, headers, HttpMethod.POST, URI.create(url));
        ResponseEntity<KakaoTokenResponse> response = restTemplate.exchange(request, KakaoTokenResponse.class);
        KakaoTokenResponse kakaoTokenResponse = Optional.ofNullable(response.getBody()).orElse(null);

        System.out.println("accessToken: " + kakaoTokenResponse.access_token());
        return ResponseEntity.ok(kakaoTokenResponse);
    }
}