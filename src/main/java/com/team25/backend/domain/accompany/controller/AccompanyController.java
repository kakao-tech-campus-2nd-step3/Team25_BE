package com.team25.backend.domain.accompany.controller;

import com.team25.backend.domain.auth.annotation.LoginUser;
import com.team25.backend.domain.accompany.dto.request.AccompanyRequest;
import com.team25.backend.domain.accompany.dto.response.AccompanyCoordinateResponse;
import com.team25.backend.domain.accompany.dto.response.AccompanyResponse;
import com.team25.backend.global.util.ApiResponse;
import com.team25.backend.domain.user.entity.User;
import com.team25.backend.domain.accompany.service.AccompanyService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccompanyController {

    private final AccompanyService accompanyService;

    public AccompanyController(AccompanyService accompanyService) {
        this.accompanyService = accompanyService;
    }

    @GetMapping("/api/tracking/{reservation_id}")
    public ResponseEntity<ApiResponse<List<AccompanyResponse>>> getTracking(
        @SuppressWarnings("unused") @LoginUser User user,
        @PathVariable(name = "reservation_id") Long reservationId) {
        return new ResponseEntity<>(
            new ApiResponse<>(true, "실시간 동행현황이 조회되었습니다.", accompanyService.getTrackingAccompanies(
                reservationId)), HttpStatus.OK);
    }

    @GetMapping("/api/tracking/{reservation_id}/location")
    public ResponseEntity<ApiResponse<List<AccompanyCoordinateResponse>>> getTrackingCoordinate(
        @SuppressWarnings("unused") @LoginUser User user,
        @PathVariable(name = "reservation_id") Long reservationId) {
        return new ResponseEntity<>(
            new ApiResponse<>(true, "실시간 동행 위치 정보가 조회되었습니다.",
                accompanyService.getTrackingCoordinates(reservationId)), HttpStatus.OK);
    }

    @PostMapping("/api/manager/tracking/{reservation_id}")
    public ResponseEntity<ApiResponse<AccompanyResponse>> postTracking(
        @SuppressWarnings("unused") @LoginUser User user,
        @PathVariable(name = "reservation_id") Long reservationId,
        @RequestBody AccompanyRequest accompanyRequest) {
        return new ResponseEntity<>(
            new ApiResponse<>(true, "실시간 동행현황이 작성되었습니다.",
                accompanyService.addTrackingAccompany(reservationId, accompanyRequest)),
            HttpStatus.CREATED);
    }
}
