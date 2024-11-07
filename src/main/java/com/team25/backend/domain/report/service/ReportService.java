package com.team25.backend.domain.report.service;

import com.team25.backend.domain.report.repository.ReportRepository;
import com.team25.backend.domain.report.dto.request.ReportRequest;
import com.team25.backend.domain.report.dto.response.ReportResponse;
import com.team25.backend.domain.report.entity.Report;
import com.team25.backend.domain.reservation.entity.Reservation;
import com.team25.backend.domain.report.enumdomain.MedicineTime;
import com.team25.backend.global.exception.ReportErrorCode;
import com.team25.backend.global.exception.ReportException;
import com.team25.backend.global.exception.ReservationErrorCode;
import com.team25.backend.global.exception.ReservationException;
import com.team25.backend.domain.reservation.repository.ReservationRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    public List<ReportResponse> getReport(Long reservationId) {
        List<Report> reports = reportRepository.findByReservation_Id(reservationId);
        if (reports.isEmpty()) {
            throw new ReservationException(ReservationErrorCode.RESERVATION_WITHOUT_REPORT);
        }
        ArrayList<ReportResponse> reportResponses = new ArrayList<>();
        for (Report report : reports) {
            reportResponses.add(new ReportResponse(report.getDoctorSummary(), report.getFrequency(),
                    report.getMedicineTime().toString(),
                    report.getTimeOfDay())
            );
        }
        return reportResponses;
    }


    // 환자 결과 리포트 생성
    @Transactional
    public ReportResponse createReport(Long reservationId, ReportRequest reportRequest) {
        validateReportRequest(reportRequest);
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReservationException(ReservationErrorCode.RESERVATION_NOT_FOUND));
        Report report = Report.builder()
            .reservation(reservation)  // 연관관계 설정
            .doctorSummary(reportRequest.doctorSummary())
            .frequency(reportRequest.frequency())
            .medicineTime(reportRequest.medicineTime())
            .timeOfDay(reportRequest.timeOfDays())
            .build();

        report = reportRepository.save(report);
        reservation.addReport(report);

        return new ReportResponse(report.getDoctorSummary(), report.getFrequency(),
            report.getMedicineTime().toString(), report.getTimeOfDay());
    }

    private static void validateReportRequest(ReportRequest reportRequest) {
        if(reportRequest.doctorSummary() == null || reportRequest.doctorSummary().isEmpty()) {
            throw new ReportException(ReportErrorCode.REQUIRED_DOCTOR_SUMMARY_MISSING);
        }
        if(!Arrays.stream(MedicineTime.values()).toList().contains(reportRequest.medicineTime())) {
            throw new ReportException(ReportErrorCode.INVALID_MEDICINE_TIME);
        }
        if(reportRequest.timeOfDays() == null || reportRequest.timeOfDays().isEmpty()) {
            throw new ReportException(ReportErrorCode.REQUIRED_TIME_OF_DAYS_MISSING);
        }
    }
}
