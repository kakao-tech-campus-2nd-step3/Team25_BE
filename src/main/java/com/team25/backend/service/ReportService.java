package com.team25.backend.service;

import com.team25.backend.dto.request.ReportRequest;
import com.team25.backend.dto.response.ReportResponse;
import com.team25.backend.entity.Report;
import com.team25.backend.entity.Reservation;
import com.team25.backend.repository.ReportRepository;
import com.team25.backend.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    public ReportResponse getReport(Long reservationId) {
        Reservation completedReservation = reservationRepository.findById(reservationId)
            .orElseThrow(IllegalArgumentException::new);
        Report report = reportRepository.findByReservation(
            completedReservation).orElseThrow(IllegalArgumentException::new);
        return new ReportResponse(report.getDoctorSummary(),
            report.getFrequency(), report.getMealTime(),
            report.getTimeOfDay());
    }

    // 환자 결과 리포트 생성
    public ReportResponse createReport(Long reservationId, ReportRequest reportRequest) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(IllegalArgumentException::new);
        Report report = Report.builder().
            doctorSummary(reportRequest.doctorSummary())
            .frequency(reportRequest.frequency())
            .mealTime(reportRequest.mealTime())
            .timeOfDay(reportRequest.timeOfDays())
            .build();
        reservation.getReports().add(report);
        reservationRepository.save(reservation);
        return new ReportResponse(report.getDoctorSummary(), report.getFrequency(),
            report.getMealTime(), report.getTimeOfDay());
    }

}
