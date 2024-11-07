package com.team25.backend.domain.user.controller;

import com.team25.backend.domain.auth.annotation.LoginUser;
import com.team25.backend.global.util.ApiResponse;
import com.team25.backend.domain.user.dto.response.UserRoleResponse;
import com.team25.backend.domain.user.dto.response.UserStatusResponse;
import com.team25.backend.domain.user.entity.User;
import com.team25.backend.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users/me/role")
    @ResponseBody
    public ResponseEntity<ApiResponse<UserRoleResponse>> myAPI(@LoginUser User user){
        UserRoleResponse userRole = new UserRoleResponse(user.getRole());
        return ResponseEntity.ok(new ApiResponse<>(true,"사용자 역할 조회를 성공했습니다.",userRole));
    }

    @GetMapping("/api/users/me/status")
    @ResponseBody
    public ResponseEntity<ApiResponse<UserStatusResponse>> myStatusAPI(@LoginUser User user){
        UserStatusResponse userStatusResponse = userService.getUserStatusById(user.getId());
        return ResponseEntity.ok(new ApiResponse<>(true,"사용자 상태 조회를 성공했습니다.",userStatusResponse));
    }

    @DeleteMapping("/api/users/withdraw")
    @ResponseBody
    public ResponseEntity<ApiResponse<?>> deleteMyAccount(@LoginUser User user){
        userService.removeUser(user.getId());
        return ResponseEntity.ok(new ApiResponse<>(true,"사용자를 삭제했습니다.",null));
    }
}
