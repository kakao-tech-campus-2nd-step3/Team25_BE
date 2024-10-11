package com.team25.backend.service;

import com.team25.backend.dto.request.LoginRequest;
import com.team25.backend.dto.request.UserRequest;
import com.team25.backend.dto.response.AccessTokenInfoResponse;
import com.team25.backend.dto.response.UserResponse;
import com.team25.backend.exception.CustomException;
import com.team25.backend.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@Slf4j
@Service
public class KakaoOAuth2Service {
    private final UserService userService;
    private final RestTemplate restTemplate;

    public KakaoOAuth2Service(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    public UserResponse processKakaoLogin(LoginRequest loginRequest){
        String accessToken = loginRequest.oauthAccessToken();
        String username = getUserInfo(accessToken);

        UserRequest userRequest = new UserRequest(username);

        if(!userService.isAlreadyUserRegister(userRequest)){
            return userService.registerUser(userRequest);
        }

        return userService.findUser(userRequest);
    }

    public String getUserInfo(String accessToken){
        String url = "https://kapi.kakao.com/v1/user/access_token_info";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        var request = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));

        var response = restTemplate.exchange(request, AccessTokenInfoResponse.class);
        AccessTokenInfoResponse tokenInfoResponse = Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new CustomException(ErrorCode.RESPONSE_BODY_NULL));

        Long id = tokenInfoResponse.id();
        return "kakao"+id;
    }
}
