package com.team25.backend.service;

import com.team25.backend.dto.CustomUserDetails;
import com.team25.backend.entity.User;
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
                    String newUserUUID = UUID.randomUUID().toString();
                    User newUser = new User(username, "ROLE_USER", newUserUUID);
                    userRepository.save(newUser);
                    return new CustomUserDetails(newUser);
                });
    }
}
