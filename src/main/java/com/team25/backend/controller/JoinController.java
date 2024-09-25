package com.team25.backend.controller;

import com.team25.backend.dto.ApiDto;
import com.team25.backend.dto.ReservationDto;
import com.team25.backend.dto.request.JoinRequest;
import com.team25.backend.dto.response.ApiResponse;
import com.team25.backend.dto.response.ManagerResponse;
import com.team25.backend.entity.Reservation;
import com.team25.backend.service.JoinService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JoinController {
    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public ResponseEntity<ApiDto> joinProcess(@RequestBody JoinRequest joinRequest) {
        joinService.joinProcess(joinRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDto<JoinRequest>(joinRequest,"회원 가입 성공", HttpStatus.CREATED));
    }
}
