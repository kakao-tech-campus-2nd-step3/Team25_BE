package com.team25.backend.service;

import com.team25.backend.dto.CustomOAuth2User;
import com.team25.backend.dto.KakaoResponse;
import com.team25.backend.dto.OAuth2Response;
import com.team25.backend.dto.UserDto;
import com.team25.backend.entity.User;
import com.team25.backend.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        String registerationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if(registerationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
            System.out.println("kakao");
        }
        else{
            return null;
        }

        String username = oAuth2Response.getProvider()+oAuth2Response.getProviderId();
        User existData = userRepository.findByUsername(username);

        if(existData == null) {
            // 유저 저장
            User user = new User();
            user.setUsername(username);
            user.setEmail(oAuth2Response.getEmail());
            //user.setName(oAuth2Response.getName());
            user.setRole(User.Role.ROLE_USER);

            userRepository.save(user);

            // 로그인 처리
            UserDto userDto = new UserDto();
            userDto.setUsername(username);
            userDto.setName(oAuth2Response.getName());
            userDto.setRole("ROLE_USER");

            return new CustomOAuth2User(userDto);
        }
        else {
            // 정보에 변경사항 있으면 업데이트
            existData.setEmail(oAuth2Response.getEmail());
            //existData.setName(oAuth2Response.getName());

            // 로그인 처리
            UserDto userDto = new UserDto();
            userDto.setUsername(username);
            userDto.setName(oAuth2Response.getName());
            userDto.setRole("ROLE_USER");

            return new CustomOAuth2User(userDto);
        }
    }
}
