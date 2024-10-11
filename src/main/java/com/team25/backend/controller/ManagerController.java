package com.team25.backend.controller;

import com.team25.backend.annotation.LoginUser;
import com.team25.backend.dto.request.*;
import com.team25.backend.dto.response.*;
import com.team25.backend.entity.User;
import com.team25.backend.service.ManagerService;
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

    @GetMapping("/api/manager/{manager_id}")
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

    @PostMapping("/api/manager/time/{manager_id}")
    public ResponseEntity<ApiResponse<ManagerWorkingHourCreateResponse>> addWorkingHour(
        @PathVariable("manager_id") Long managerId,
        @RequestBody ManagerWorkingHourCreateRequest request) {

        ManagerWorkingHourCreateResponse response = managerService.addWorkingHour(managerId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.<ManagerWorkingHourCreateResponse>builder()
                .status(true)
                .message("근무 시간을 성공적으로 등록했습니다.")
                .data(response)
                .build());
    }

    @PatchMapping("/api/manager/image/{manager_id}")
    public ResponseEntity<ApiResponse<ManagerProfileImageUpdateResponse>> updateProfileImage(
        @PathVariable("manager_id") Long managerId,
        @RequestBody ManagerProfileImageUpdateRequest request) {

        ManagerProfileImageUpdateResponse response = managerService.updateProfileImage(managerId, request);

        return ResponseEntity.ok(
            ApiResponse.<ManagerProfileImageUpdateResponse>builder()
                .status(true)
                .message("프로필 사진을 성공적으로 변경했습니다.")
                .data(response)
                .build()
        );
    }

    @PatchMapping("/api/manager/comment/{manager_id}")
    public ResponseEntity<ApiResponse<ManagerCommentUpdateResponse>> updateComment(
        @PathVariable("manager_id") Long managerId,
        @RequestBody ManagerCommentUpdateRequest request) {

        ManagerCommentUpdateResponse response = managerService.updateComment(managerId, request);

        return ResponseEntity.ok(
            ApiResponse.<ManagerCommentUpdateResponse>builder()
                .status(true)
                .message("코멘트를 성공적으로 변경했습니다.")
                .data(response)
                .build()
        );
    }

    @PatchMapping("/api/manager/location/{manager_id}")
    public ResponseEntity<ApiResponse<ManagerLocationUpdateResponse>> updateLocation(
        @PathVariable("manager_id") Long managerId,
        @RequestBody ManagerLocationUpdateRequest request) {

        ManagerLocationUpdateResponse response = managerService.updateLocation(managerId, request);

        return ResponseEntity.ok(
            ApiResponse.<ManagerLocationUpdateResponse>builder()
                .status(true)
                .message("근무 지역을 성공적으로 변경했습니다.")
                .data(response)
                .build()
        );
    }

    @PutMapping("/api/manager/time/{manager_id}")
    public ResponseEntity<ApiResponse<ManagerWorkingHourUpdateResponse>> updateWorkingHour(
        @PathVariable("manager_id") Long managerId,
        @RequestBody ManagerWorkingHourUpdateRequest request) {

        ManagerWorkingHourUpdateResponse response = managerService.updateWorkingHour(managerId, request);

        return ResponseEntity.ok(
            ApiResponse.<ManagerWorkingHourUpdateResponse>builder()
                .status(true)
                .message("근무 시간을 성공적으로 변경했습니다.")
                .data(response)
                .build()
        );
    }
}
