package com.team25.backend.controller;

import com.team25.backend.annotation.LoginUser;
import com.team25.backend.dto.request.CancelRequest;
import com.team25.backend.dto.request.ReservationRequest;
import com.team25.backend.dto.response.ApiResponse;
import com.team25.backend.dto.response.ReservationResponse;
import com.team25.backend.entity.Reservation;
import com.team25.backend.entity.User;
import com.team25.backend.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReservationResponse>> create(
        @LoginUser User user,
        @Valid @RequestBody ReservationRequest reservationRequest) {
        return new ResponseEntity<>(new ApiResponse<>(true, "예약이 접수되었습니다",
            reservationService.createReservation(reservationRequest)
        ), HttpStatus.CREATED);
    }

    @PatchMapping("/cancel/{reservation_id}") // 이미 취소된 것을 다시 또 취소하는 경우 에러 처리 필요
    public ResponseEntity<ApiResponse<ReservationResponse>> cancel(
        @LoginUser User user,
        @PathVariable(value = "reservation_id") Long reservation_id,
        @Valid @RequestBody CancelRequest cancelRequest) {
        return new ResponseEntity<>(new ApiResponse<>(true, "예약 취수가 접수되었습니다",
            reservationService.cancelReservation(reservation_id, cancelRequest)), HttpStatus.OK);
    }
}
