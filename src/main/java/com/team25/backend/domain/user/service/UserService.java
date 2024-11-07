package com.team25.backend.domain.user.service;

import com.team25.backend.domain.user.repository.UserRepository;
import com.team25.backend.domain.user.dto.request.UserRequest;
import com.team25.backend.domain.admin.dto.response.AdminPageUserInfoResponse;
import com.team25.backend.domain.user.dto.response.UserResponse;
import com.team25.backend.domain.user.dto.response.UserStatusResponse;
import com.team25.backend.domain.user.entity.User;
import com.team25.backend.global.exception.CustomException;
import com.team25.backend.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.team25.backend.global.exception.ErrorCode.USER_NOT_FOUND;

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
                .orElseThrow(()->new CustomException(USER_NOT_FOUND));

        return new UserResponse(foundUser.getUsername(), foundUser.getUuid(), foundUser.getRole());
    }

    public void removeUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        userRepository.deleteById(userId);
    }

    public List<AdminPageUserInfoResponse> getAllUsersForAdminPage() {
        return userRepository.findAll().stream().map(user -> {
            String description;

            if (user.getManager() == null && "ROLE_USER".equals(user.getRole())) {
                description = "일반 유저";
            } else if (user.getManager() != null && "ROLE_USER".equals(user.getRole())) {
                description = "매니저 승인 대기";
            } else if (user.getManager() != null && "ROLE_MANAGER".equals(user.getRole())) {
                description = "매니저";
            } else {
                description = "기타";
            }

            return new AdminPageUserInfoResponse(user.getId(), user.getUsername(), user.getRole(), description);
        }).collect(Collectors.toList());
    }

    public UserStatusResponse getUserStatusById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        String description = "USER";
        if (user.getManager() == null && "ROLE_USER".equals(user.getRole())) {
            description = "USER";
        } else if (user.getManager() != null && "ROLE_USER".equals(user.getRole())) {
            description = "MANAGER_PENDING";
        } else if (user.getManager() != null && "ROLE_MANAGER".equals(user.getRole())) {
            description = "MANAGER";
        }

        return new UserStatusResponse(description);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}