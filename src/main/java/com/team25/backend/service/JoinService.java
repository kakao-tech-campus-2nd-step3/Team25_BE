package com.team25.backend.service;

import com.team25.backend.dto.request.JoinRequest;
import com.team25.backend.entity.User;
import com.team25.backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinRequest joinRequest) {

        String username = joinRequest.getUsername();

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {
            return;
        }

        User data = new User();

        data.setUsername(username);
        data.setRole("ROLE_USER");

        userRepository.save(data);
    }
}
