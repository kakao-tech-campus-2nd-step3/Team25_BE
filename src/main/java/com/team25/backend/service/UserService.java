package com.team25.backend.service;

import com.team25.backend.dto.request.UserRequest;
import com.team25.backend.dto.response.UserResponse;
import com.team25.backend.entity.User;
import com.team25.backend.exception.CustomException;
import com.team25.backend.exception.ErrorCode;
import com.team25.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse registerUser(UserRequest userRequest){
        if(userRepository.existsByUsername(userRequest.username())){
            throw new CustomException(ErrorCode.USER_ALREADY_EXISTS);
        }

        String newUserUUID = UUID.randomUUID().toString();
        User newUser = new User(userRequest.username(), newUserUUID,"ROLE_USER");

        User savedUser = userRepository.save(newUser);

        return new UserResponse(savedUser.getUsername(), savedUser.getUuid(), savedUser.getRole());
    }

    public boolean isAlreadyUserRegister(UserRequest userRequest){
        return userRepository.existsByUsername(userRequest.username());
    }

    public UserResponse findUser(UserRequest userRequest){
        User foundUser = userRepository.findByUsername(userRequest.username())
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));

        return new UserResponse(foundUser.getUsername(), foundUser.getUuid(), foundUser.getRole());
    }
}