package com.team25.backend.domain.manager.controller;

import com.team25.backend.domain.auth.annotation.LoginUser;
import com.team25.backend.domain.manager.dto.request.*;
import com.team25.backend.domain.manager.dto.response.*;
import com.team25.backend.global.util.ApiResponse;
import com.team25.backend.domain.user.entity.User;
import com.team25.backend.domain.manager.service.ManagerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/api/managers")
    public ResponseEntity<ApiResponse<List<ManagerByDateAndRegionResponse>>> getManagers(
        @RequestParam("date") String date,
        @RequestParam("region") String region) {

        List<ManagerByDateAndRegionResponse> managerResponses = managerService.getManagersByDateAndRegion(date, region);

        ApiResponse<List<ManagerByDateAndRegionResponse>> response = ApiResponse.<List<ManagerByDateAndRegionResponse>>builder()
            .status(true)
            .message("매니저 조회에 성공하였습니다.")
            .data(managerResponses)
            .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/manager")
    public ResponseEntity<ApiResponse<ManagerCreateResponse>> createManager(
            @LoginUser User user, @RequestBody ManagerCreateRequest request) {

        ManagerCreateResponse response = managerService.createManager(user, request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.<ManagerCreateResponse>builder()
                .status(true)
                .message("매니저 등록을 성공했습니다.")
                .data(response)
                .build());
    }

    @GetMapping("/api/manager/profile/{manager_id}")
    public ResponseEntity<ApiResponse<ManagerProfileResponse>> getManagerProfile(
        @PathVariable("manager_id") Long managerId) {

        ManagerProfileResponse response = managerService.getManagerProfile(managerId);

        return ResponseEntity.ok(
            ApiResponse.<ManagerProfileResponse>builder()
                .status(true)
                .message("프로필 조회를 성공했습니다.")
                .data(response)
                .build()
        );
    }

    @GetMapping("/api/manager/me/profile")
    public ResponseEntity<ApiResponse<ManagerProfileResponse>> getManagerProfile(
        @LoginUser User user) {

        ManagerProfileResponse response = managerService.getManagerProfile(user);

        return ResponseEntity.ok(
            ApiResponse.<ManagerProfileResponse>builder()
                .status(true)
                .message("프로필 조회를 성공했습니다.")
                .data(response)
                .build()
        );
    }

    @PostMapping("/api/manager/time")
    public ResponseEntity<ApiResponse<ManagerWorkingHourUpdateResponse>> addWorkingHour(
        @LoginUser User user,
        @RequestBody ManagerWorkingHourUpdateRequest request) {

        ManagerWorkingHourUpdateResponse response = managerService.updateWorkingHour(user, request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.<ManagerWorkingHourUpdateResponse>builder()
                .status(true)
                .message("근무 시간을 성공적으로 변경했습니다.")
                .data(response)
                .build());
    }

    @PatchMapping("/api/manager/image")
    public ResponseEntity<ApiResponse<ManagerProfileImageUpdateResponse>> updateProfileImage(
        @LoginUser User user,
        @RequestBody ManagerProfileImageUpdateRequest request) {

        ManagerProfileImageUpdateResponse response = managerService.updateProfileImage(user, request);

        return ResponseEntity.ok(
            ApiResponse.<ManagerProfileImageUpdateResponse>builder()
                .status(true)
                .message("프로필 사진을 성공적으로 변경했습니다.")
                .data(response)
                .build()
        );
    }

    @PatchMapping("/api/manager/comment")
    public ResponseEntity<ApiResponse<ManagerCommentUpdateResponse>> updateComment(
        @LoginUser User user,
        @RequestBody ManagerCommentUpdateRequest request) {

        ManagerCommentUpdateResponse response = managerService.updateComment(user, request);

        return ResponseEntity.ok(
            ApiResponse.<ManagerCommentUpdateResponse>builder()
                .status(true)
                .message("코멘트를 성공적으로 변경했습니다.")
                .data(response)
                .build()
        );
    }

    @PatchMapping("/api/manager/location")
    public ResponseEntity<ApiResponse<ManagerLocationUpdateResponse>> updateLocation(
        @LoginUser User user,
        @RequestBody ManagerLocationUpdateRequest request) {

        ManagerLocationUpdateResponse response = managerService.updateLocation(user, request);

        return ResponseEntity.ok(
            ApiResponse.<ManagerLocationUpdateResponse>builder()
                .status(true)
                .message("근무 지역을 성공적으로 변경했습니다.")
                .data(response)
                .build()
        );
    }

    @GetMapping("/api/manager/name")
    @ResponseBody
    public ResponseEntity<ApiResponse<ManagerNameResponse>> getManagerName(@LoginUser User user){
        ManagerNameResponse managerName = managerService.findManagerNameByUserId(user.getId());
        return ResponseEntity.ok(new ApiResponse<>(true,"매니저 이름 조회를 성공했습니다.", managerName));
    }
}
