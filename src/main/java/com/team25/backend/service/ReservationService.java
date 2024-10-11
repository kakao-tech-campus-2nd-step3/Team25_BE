package com.team25.backend.service;

import static com.team25.backend.exception.ReservationErrorCode.MANAGER_NOT_FOUND;
import static com.team25.backend.exception.ReservationErrorCode.MANAGER_REQUIRED;
import static com.team25.backend.exception.ReservationErrorCode.RESERVATION_ALREADY_CANCELED;
import static com.team25.backend.exception.ReservationErrorCode.RESERVATION_NOT_BELONG_TO_USER;
import static com.team25.backend.exception.ReservationErrorCode.RESERVATION_NOT_FOUND;
import static com.team25.backend.exception.ReservationErrorCode.USER_HAS_NO_RESERVATIONS;
import static com.team25.backend.exception.ReservationErrorCode.USER_NOT_FOUND;

import com.team25.backend.dto.request.CancelRequest;
import com.team25.backend.dto.request.ReservationRequest;
import com.team25.backend.dto.response.ReservationResponse;
import com.team25.backend.entity.Manager;
import com.team25.backend.entity.Patient;
import com.team25.backend.entity.Reservation;
import com.team25.backend.entity.User;
import com.team25.backend.enumdomain.CancelReason;
import com.team25.backend.enumdomain.ReservationStatus;
import com.team25.backend.exception.ReservationException;
import com.team25.backend.repository.ManagerRepository;
import com.team25.backend.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ManagerRepository managerRepository;
    private final PatientService patientService;

    public ReservationService(ReservationRepository reservationRepository,
        ManagerRepository managerRepository
        , PatientService patientService) {
        this.reservationRepository = reservationRepository;
        this.managerRepository = managerRepository;
        this.patientService = patientService;
    }

    // 예약 전체 조회
    public List<ReservationResponse> getAllReservations(User user) {
        List<Reservation> reservations = reservationRepository.findByUser_Uuid(user.getUuid());
        if(reservations.isEmpty()) {
           throw  new ReservationException(USER_NOT_FOUND);
        }
        List<ReservationResponse> responseList = new ArrayList<>();
        for (Reservation reservation : reservations) {
            responseList.add(
                new ReservationResponse(
                    reservation.getDepartureLocation(),
                    reservation.getArrivalLocation(),
                    reservation.getReservationDateTime(),
                    reservation.getServiceType(),
                    reservation.getTransportation(),
                    reservation.getPrice()
                )
            );
        }
        return responseList;
    }

    // 단일 예약 조회
    public ReservationResponse getReservationById(User user, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));
        List<Reservation> reservations = reservationRepository.findByUser_Uuid(user.getUuid());
        if(reservations.isEmpty()) {
            throw new ReservationException(USER_NOT_FOUND);
        }
        if (!reservations.contains(reservation)) {
            throw new ReservationException(RESERVATION_NOT_BELONG_TO_USER);
        }
        return new ReservationResponse(
            reservation.getDepartureLocation(),
            reservation.getArrivalLocation(),
            reservation.getReservationDateTime(),
            reservation.getServiceType(),
            reservation.getTransportation(),
            reservation.getPrice()
        );
    }

    // 예약 작성
    public ReservationResponse createReservation(ReservationRequest reservationRequest, User user) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime reservationDateTime =
                LocalDateTime.parse(reservationRequest.reservationDateTime(), formatter);
            if (reservationRequest.managerId() == null) {
                throw new ReservationException(MANAGER_REQUIRED);
            }
            Manager manager = managerRepository.findById(reservationRequest.managerId())
                .orElseThrow(() -> new ReservationException(MANAGER_NOT_FOUND));
            Patient patient = patientService.addPatient(reservationRequest.patient());
            Reservation reservation = Reservation.builder()
                .departureLocation(reservationRequest.departureLocation())
                .arrivalLocation(reservationRequest.arrivalLocation())
                .reservationDateTime(reservationDateTime)
                .serviceType(reservationRequest.serviceType())
                .transportation(reservationRequest.transportation())
                .price(reservationRequest.price())
                .createdTime(LocalDateTime.now())
                .reservationStatus(ReservationStatus.CONFIRMED)
                .patient(patient)
                .manager(manager)
                .user(user)
                .build();

            reservationRepository.save(reservation);
            return new ReservationResponse(
                reservation.getDepartureLocation(),
                reservation.getArrivalLocation(),
                reservation.getReservationDateTime(),
                reservation.getServiceType(),
                reservation.getTransportation(),
                reservation.getPrice());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    // 예약 취소
    @Transactional
    public ReservationResponse cancelReservation(User user, CancelRequest cancelRequest,
        Long reservationId) {
        List<Reservation> reservations = reservationRepository.findByUser_Uuid(user.getUuid());
        if(reservations.isEmpty()) {
            throw new ReservationException(USER_NOT_FOUND);
        }
        Reservation canceledReservation = reservations.stream()
            .filter(x -> x.getId().equals(reservationId)).findFirst()
            .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));
        if (canceledReservation.getReservationStatus() == ReservationStatus.CANCEL) {
            throw new ReservationException(RESERVATION_ALREADY_CANCELED);
        }
        CancelReason cancelReason = cancelRequest.cancelReason();

        addCancelReasonAndDetail(canceledReservation, cancelReason,
            cancelRequest.cancelDetail()); // 예약에 취소 사유와 상세 정보 추가
        reservationRepository.save(canceledReservation);
        return new ReservationResponse(canceledReservation.getDepartureLocation(),
            canceledReservation.getArrivalLocation(),
            canceledReservation.getReservationDateTime(),
            canceledReservation.getServiceType(),
            canceledReservation.getTransportation(),
            canceledReservation.getPrice());
    }

    private static void addCancelReasonAndDetail(Reservation canceledReservation,
        CancelReason cancelReason, String cancelDetail) {
        canceledReservation.setCancelReason(cancelReason);
        canceledReservation.setCancelDetail(cancelDetail);
        canceledReservation.setReservationStatus(ReservationStatus.CANCEL);
    }

    private static void checkDetailIsNull(CancelRequest cancelRequest) {
        if (cancelRequest.cancelDetail().isBlank()) {
            throw new IllegalArgumentException("변심 이유를 반드시 선택해야 합니다.");
        }
    }
}
