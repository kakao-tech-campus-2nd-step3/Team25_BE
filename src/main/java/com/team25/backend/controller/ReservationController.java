package com.team25.backend.controller;

import com.team25.backend.annotation.LoginUser;
import com.team25.backend.dto.request.CancelRequest;
import com.team25.backend.dto.request.ReservationRequest;
import com.team25.backend.dto.response.ApiResponse;
import com.team25.backend.dto.response.ReservationResponse;
import com.team25.backend.entity.User;
import com.team25.backend.service.ReservationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReservationResponse>> createReservation(
        @LoginUser User user,
        @Valid @RequestBody ReservationRequest reservationRequest) {
        return new ResponseEntity<>(new ApiResponse<>(true, "예약이 접수되었습니다",
            reservationService.createReservation(reservationRequest, user)
        ), HttpStatus.CREATED);
    }

    @PatchMapping("/cancel/{reservation_id}") // 이미 취소된 것을 다시 또 취소하는 경우 에러 처리 필요
    public ResponseEntity<ApiResponse<ReservationResponse>> cancelReservation(
        @LoginUser User user,@PathVariable(name = "reservation_id") Long reservationId,
        @Valid @RequestBody CancelRequest cancelRequest) {
        return new ResponseEntity<>(new ApiResponse<>(true, "예약 취수가 접수되었습니다",
            reservationService.cancelReservation(user, cancelRequest,reservationId)), HttpStatus.OK);
    }

    @GetMapping("/{reservation_id}")
    public ResponseEntity<ApiResponse<ReservationResponse>> getReservations(
        @LoginUser User user,
        @PathVariable(name = "reservation_id") Long reservationId
    ) {
        return new ResponseEntity<>(
            new ApiResponse<>(true, "예악 조회가 성공하였습니다.",
                reservationService.getReservationById(user, reservationId)
            ), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getReservations(
        @LoginUser User user
    ) {
        return new ResponseEntity<>(
            new ApiResponse<>(true, "사용자의 예약 목록을 조회하였습니다.",
                reservationService.getAllReservations(user)
            ), HttpStatus.OK);
    }
}
