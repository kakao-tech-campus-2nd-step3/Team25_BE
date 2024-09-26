package com.team25.backend.controller;

import com.team25.backend.dto.request.ManagerCreateRequest;
import com.team25.backend.dto.request.ManagerProfileImageUpdateRequest;
import com.team25.backend.dto.request.ManagerWorkingHourRequest;
import com.team25.backend.dto.response.*;
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
        @RequestBody ManagerCreateRequest request) {

        ManagerCreateResponse response = managerService.createManager(request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.<ManagerCreateResponse>builder()
                .status(true)
                .message("매니저 등록을 성공했습니다.")
                .data(response)
                .build());
    }

    @GetMapping("/api/profile/{manager_id}")
    public ResponseEntity<ApiResponse<ManagerProfileResponse>> getManagerProfile(
        @PathVariable("manager_id") Long managerId) {

        ManagerProfileResponse profileResponse = managerService.getManagerProfile(managerId);

        return ResponseEntity.ok(
            ApiResponse.<ManagerProfileResponse>builder()
                .status(true)
                .message("프로필 조회를 성공했습니다.")
                .data(profileResponse)
                .build()
        );
    }

    @PostMapping("/api/manager/time/{manager_id}")
    public ResponseEntity<ApiResponse<ManagerWorkingHourResponse>> addWorkingHour(
        @PathVariable("manager_id") Long managerId,
        @RequestBody ManagerWorkingHourRequest request) {

        ManagerWorkingHourResponse response = managerService.addWorkingHour(managerId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.<ManagerWorkingHourResponse>builder()
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
}
