package com.team25.backend.service;

import com.team25.backend.dto.CancelDto;
import com.team25.backend.dto.ReservationDto;
import com.team25.backend.entity.Reservation;
import com.team25.backend.enumdomain.CancelReason;
import com.team25.backend.enumdomain.ReservationStatus;
import com.team25.backend.repository.ReservationRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // 예약 작성
    public Reservation createReservation(ReservationDto reservationDto) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime reservationDateTime = LocalDateTime.parse(
                reservationDto.reservationDateTime(), formatter);
            Reservation reservation = Reservation.builder()
                .departureLocation(reservationDto.departureLocation())
                .arrivalLocation(reservationDto.arrivalLocation())
                .reservationDateTime(reservationDateTime).serviceType(reservationDto.serviceType())
                .transportation(reservationDto.transportation()).price(Integer.parseInt(reservationDto.price()))
                .createdTime(LocalDateTime.now()).reservationStatus(ReservationStatus.CONFIRMED)
                .build();
            reservationRepository.save(reservation);
            return reservation;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("보호자 전화번호를 반드시 입력해야 합니다.");
        }
        // 401 에러는 SpringSecurity에서 처리
    }

    // 예약 취소
    public Reservation cancelReservation(Long reservationId, CancelDto cancelDto) {
        // 해당 reservationDTO를 통해 특정 예약을 어떻게 하면 잡아낼 수 있는가?
        // checkDetailIsNull(cancelDto); // cancelDto에 상세 사유 없으면 예외 처리
        Reservation canceledReservation = reservationRepository.findById(reservationId) // reservationId로 예약 데이터 찾기
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
        CancelReason cancelReason = Arrays.stream(CancelReason.values()) // 해당 취소 이유를 Enum 타입에서 선별
            .filter(reason -> reason.getKrName().equals(cancelDto.cancelReason())).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 취소 타입입니다."));
        addCancelReasonAndDetail(canceledReservation, cancelReason, cancelDto.cancelDetail()); // 예약에 취소 사유와 상세 정보 추가
        reservationRepository.save(canceledReservation);
        return canceledReservation;
    }

    private static void addCancelReasonAndDetail(Reservation canceledReservation,
        CancelReason cancelReason, String cancelDetail) {
        canceledReservation.setCancelReason(cancelReason);
        canceledReservation.setCancelDetail(cancelDetail);
    }

    private static void checkDetailIsNull(CancelDto cancelDto) {
        if (cancelDto.cancelDetail().isBlank()) {
            throw new IllegalArgumentException("변심 이유를 반드시 선택해야 합니다.");
        }
    }
}
