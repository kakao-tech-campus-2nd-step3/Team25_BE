package com.team25.backend.service;

import com.team25.backend.dto.CustomUserDetails;
import com.team25.backend.entity.User;
import com.team25.backend.exception.UserNotFoundException;
import com.team25.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 회원이 존재하지 않으면, 가입 시킴
        if (userRepository.existsByUsername(username)) {
            User userData = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("회원이 존재하지 않습니다."));
            return new CustomUserDetails(userData);
        } else{
            User data = new User();

            data.setUsername(username);
            data.setRole("ROLE_USER");

            userRepository.save(data);
            return new CustomUserDetails(data);
        }
    }
}
