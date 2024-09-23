package com.team25.backend.controller;

import com.team25.backend.dto.response.ApiResponse;
import com.team25.backend.dto.response.ManagerByDateAndRegionResponse;
import com.team25.backend.service.ManagerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
