package com.team25.backend.controller;

import com.team25.backend.annotation.LoginUser;
import com.team25.backend.dto.request.ReportRequest;
import com.team25.backend.dto.response.ApiResponse;
import com.team25.backend.dto.response.ReportResponse;
import com.team25.backend.entity.Report;
import com.team25.backend.entity.User;
import com.team25.backend.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{reservation_id}")
    public ResponseEntity<ApiResponse<ReportResponse>> getReport(
        @LoginUser User user,
        @PathVariable("reservation_id") Long reservationId) {
        return new ResponseEntity<>(
            new ApiResponse<>(true, "리포트 조회를 성공했습니다", reportService.getReport(reservationId)),
            HttpStatus.OK);
    }

    @PostMapping("/{reservation_id}")
    public ResponseEntity<ApiResponse<ReportResponse>> createReport(
        @LoginUser User user,
        @PathVariable("reservation_id") Long reservationId,
        @Valid @RequestBody ReportRequest reportRequest) {
        return new ResponseEntity<>(new ApiResponse<>(true, "리포트 생성이 완료되었습니다",
            reportService.createReport(reservationId, reportRequest)), HttpStatus.CREATED);
    }
    // 보호자도 신청 가능: 카톡으로 진행 상황을 보여주고 있다 => 보호자로 등록한 사람한테도알림이 필요. 중간 단계마다 알림
    // 기획서가 다 보여주지 않음 =>
}
