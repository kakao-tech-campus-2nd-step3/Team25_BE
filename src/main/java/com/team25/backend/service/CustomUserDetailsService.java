package com.team25.backend.service;

import com.team25.backend.dto.CustomUserDetails;
import com.team25.backend.entity.User;
import com.team25.backend.exception.UserNotFoundException;
import com.team25.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(CustomUserDetails::new)
                .orElseGet(() -> {
                    User newUser = new User();
                    String userUUID = UUID.randomUUID().toString();
                    newUser.setUsername(username);
                    newUser.setUuid(userUUID);
                    newUser.setRole("ROLE_USER");
                    userRepository.save(newUser);
                    return new CustomUserDetails(newUser);
                });
    }
}
