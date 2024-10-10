package com.team25.backend.service;

import com.team25.backend.dto.request.ReportRequest;
import com.team25.backend.dto.response.ReportResponse;
import com.team25.backend.entity.Report;
import com.team25.backend.entity.Reservation;
import com.team25.backend.repository.ReportRepository;
import com.team25.backend.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Slf4j
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReservationRepository reservationRepository;

    public ReportService(ReportRepository reportRepository,
        ReservationRepository reservationRepository) {
        this.reportRepository = reportRepository;
        this.reservationRepository = reservationRepository;
    }

    // 리포트 조회
    @Transactional(readOnly = true)
    public ReportResponse getReport(Long reservationId) {
        Report report = reportRepository.findByReservation_Id(reservationId)
            .orElseThrow(() -> new IllegalArgumentException("해당 예약에 대한 리포트가 없습니다."));
        return new ReportResponse(
            report.getDoctorSummary(), report.getFrequency(), report.getMealTime().toString(),
            report.getTimeOfDay());
    }

    // 환자 결과 리포트 생성
    @Transactional
    public ReportResponse createReport(Long reservationId, ReportRequest reportRequest) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));

        Report report = Report.builder()
            .reservation(reservation)  // 연관관계 설정
            .doctorSummary(reportRequest.doctorSummary())
            .frequency(reportRequest.frequency())
            .mealTime(reportRequest.medicineTime())
            .timeOfDay(reportRequest.timeOfDays())
            .build();

        report = reportRepository.save(report);  // Report 엔티티 저장
        reservation.addReport(report);  // 양방향 관계 설정

        log.info("Created report for reservation ID: {}", reservationId);

        return new ReportResponse(report.getDoctorSummary(), report.getFrequency(),
            report.getMealTime().toString(), report.getTimeOfDay());
    }
}
