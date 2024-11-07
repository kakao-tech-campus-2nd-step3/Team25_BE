package com.team25.backend.domain.accompany.service;

import com.team25.backend.domain.accompany.repository.AccompanyRepository;
import com.team25.backend.domain.accompany.dto.request.AccompanyRequest;
import com.team25.backend.domain.accompany.dto.response.AccompanyCoordinateResponse;
import com.team25.backend.domain.accompany.dto.response.AccompanyResponse;
import com.team25.backend.domain.accompany.entity.Accompany;
import com.team25.backend.domain.reservation.entity.Reservation;
import com.team25.backend.domain.accompany.enumdomain.AccompanyStatus;
import com.team25.backend.global.exception.AccompanyErrorCode;
import com.team25.backend.global.exception.AccompanyException;
import com.team25.backend.global.exception.ReservationErrorCode;
import com.team25.backend.global.exception.ReservationException;
import com.team25.backend.domain.reservation.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class AccompanyService {

    private final ReservationRepository reservationRepository;
    private final AccompanyRepository accompanyRepository;

    public AccompanyService(AccompanyRepository accompanyRepository,
        ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
        this.accompanyRepository = accompanyRepository;
    }

    public List<AccompanyResponse> getTrackingAccompanies(Long reservationId) {
        checkReservationNull(reservationId);
        List<Accompany> accompanies = accompanyRepository.findByReservation_id(reservationId);
        checkListEmpty(accompanies);
        return accompanies.stream().map(AccompanyService::getAccompanyResponse)
            .peek(response -> log.info("Accompany details: {}", response)).toList();
    }

    public List<AccompanyCoordinateResponse> getTrackingCoordinates(Long reservationId) {
        checkReservationNull(reservationId);
        List<Accompany> searchedAccompanies = accompanyRepository.findByReservation_id(reservationId);
        checkListEmpty(searchedAccompanies);
        return searchedAccompanies.stream().map(AccompanyService::getAccompanyCoordinateResponse)
            .peek(reseponse -> log.info("Accompany details: {}", reseponse)).toList();
    }

    public AccompanyResponse addTrackingAccompany(Long reservationId,
        AccompanyRequest accompanyRequest) {
        validateAccompanyRequest(accompanyRequest);
        LocalDateTime accompanyDateTime = getLocalDateTime(accompanyRequest);
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
            () -> new ReservationException(ReservationErrorCode.RESERVATION_NOT_FOUND));
        Accompany track = getAccompany(accompanyRequest, accompanyDateTime);
        accompanyRepository.save(track);
        reservation.addAccompany(track);
        reservationRepository.save(reservation);
        return getAccompanyResponse(track);
    }

    private static LocalDateTime getLocalDateTime(AccompanyRequest accompanyRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(accompanyRequest.statusDate(), formatter);
    }

    private static AccompanyResponse getAccompanyResponse(Accompany track) {
        return new AccompanyResponse(track.getAccompanyStatus(), track.getTime(),
            track.getDetail());
    }

    private static AccompanyCoordinateResponse getAccompanyCoordinateResponse(Accompany track) {
        return new AccompanyCoordinateResponse(track.getLatitude(), track.getLongitude());
    }

    private static Accompany getAccompany(AccompanyRequest accompanyRequest,
        LocalDateTime accompanyDateTime) {
        return Accompany.builder().accompanyStatus(accompanyRequest.status())
            .time(accompanyDateTime).latitude(accompanyRequest.latitude())
            .longitude(accompanyRequest.longitude()).detail(accompanyRequest.statusDescribe())
            .build();
    }

    private void checkReservationNull(Long reservationId) {
        if (reservationRepository.findById(reservationId).isEmpty()) {
            throw new ReservationException(ReservationErrorCode.RESERVATION_NOT_FOUND);
        }
    }

    private static void checkListEmpty(List<Accompany> searchedAccompanies) {
        if (searchedAccompanies.isEmpty()) {
            throw new ReservationException(ReservationErrorCode.RESERVATION_WITHOUT_ACCOMPANY);
        }
    }

    private static void validateAccompanyRequest(AccompanyRequest accompanyRequest) {
        if(!Arrays.stream(AccompanyStatus.values()).toList().contains(accompanyRequest.status())) {
            throw new AccompanyException(AccompanyErrorCode.INVALID_ACCOMPANY_STATUS);
        }
        if( accompanyRequest.latitude() == null || accompanyRequest.latitude() < 0 || accompanyRequest.latitude() > 90 ) {
            throw new AccompanyException(AccompanyErrorCode.INVALID_LATITUDE);
        }
        if( accompanyRequest.longitude() == null || accompanyRequest.longitude() < 0 || accompanyRequest.longitude() > 90 ) {
            throw new AccompanyException(AccompanyErrorCode.INVALID_LONGITUDE);
        }
        if(accompanyRequest.statusDate().isEmpty()){
            throw new AccompanyException(AccompanyErrorCode.REQUIRED_DATE_MISSING);
        }
        if(accompanyRequest.statusDescribe().isEmpty()){
            throw new AccompanyException(AccompanyErrorCode.REQUIRED_DESCRIPTION_MISSING);
        }
    }
}
