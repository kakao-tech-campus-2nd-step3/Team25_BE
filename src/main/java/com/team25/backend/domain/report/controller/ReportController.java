package com.team25.backend.domain.report.controller;

import com.team25.backend.domain.auth.annotation.LoginUser;
import com.team25.backend.domain.report.dto.request.ReportRequest;
import com.team25.backend.global.util.ApiResponse;
import com.team25.backend.domain.report.dto.response.ReportResponse;
import com.team25.backend.domain.user.entity.User;
import com.team25.backend.domain.report.service.ReportService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/api/reports/{reservation_id}")
    public ResponseEntity<ApiResponse<List<ReportResponse>>> getReport(
        @SuppressWarnings("unused") @LoginUser User user,
        @PathVariable("reservation_id") Long reservationId) {
        return new ResponseEntity<>(
            new ApiResponse<>(true, "리포트 조회를 성공했습니다", reportService.getReport(reservationId)),
            HttpStatus.OK);
    }

    @PostMapping("/api/manager/reports/{reservation_id}")
    public ResponseEntity<ApiResponse<ReportResponse>> createReport(
        @SuppressWarnings("unused") @LoginUser User user,
        @PathVariable("reservation_id") Long reservationId,
        @Valid @RequestBody ReportRequest reportRequest) {
        return new ResponseEntity<>(new ApiResponse<>(true, "리포트 생성이 완료되었습니다",
            reportService.createReport(reservationId, reportRequest)), HttpStatus.CREATED);
    }
}
