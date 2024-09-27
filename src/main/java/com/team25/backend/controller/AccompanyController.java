package com.team25.backend.controller;

import com.team25.backend.annotation.LoginUser;
import com.team25.backend.dto.request.AccompanyRequest;
import com.team25.backend.dto.response.AccompanyResponse;
import com.team25.backend.dto.response.ApiResponse;
import com.team25.backend.entity.User;
import com.team25.backend.service.AccompanyService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tracking")
public class AccompanyController {

    private final AccompanyService accompanyService;

    public AccompanyController(AccompanyService accompanyService) {
        this.accompanyService = accompanyService;
    }

    @GetMapping("/{reservation_id}")
    public ResponseEntity<ApiResponse<List<AccompanyResponse>>> getTracking(
        @LoginUser User user,
        @PathVariable(name = "reservation_id") Long reservationId) {
        List<AccompanyResponse> trackingAccompanies = accompanyService.getTrackingAccompanies(
            reservationId);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "성공", trackingAccompanies), HttpStatus.OK);
    }

    @PostMapping("/{reservation_id}")
    public ResponseEntity<ApiResponse<AccompanyResponse>> postTracking(
        @LoginUser User user,
        @PathVariable(name = "reservation_id") Long reservationId,
        AccompanyRequest accompanyRequest) {
        return new ResponseEntity<>(
            new ApiResponse<>(true, "성공",
                accompanyService.addTrackingAccompany(reservationId, accompanyRequest)),
            HttpStatus.CREATED);
    }
}
