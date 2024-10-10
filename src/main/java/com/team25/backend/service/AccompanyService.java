package com.team25.backend.service;

import com.team25.backend.dto.request.AccompanyRequest;
import com.team25.backend.dto.response.AccompanyResponse;
import com.team25.backend.entity.Accompany;
import com.team25.backend.entity.Reservation;
import com.team25.backend.exception.ReservationErrorCode;
import com.team25.backend.exception.ReservationException;
import com.team25.backend.repository.AccompanyRepository;
import com.team25.backend.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        List<Accompany> accompanies = accompanyRepository.findByReservation_id(reservationId)
            .orElseThrow(() -> new ReservationException(ReservationErrorCode.RESERVATION_NOT_FOUND));

        return accompanies.stream()
            .map(accompany -> new AccompanyResponse(
                accompany.getAccompanyStatus(),
                accompany.getLatitude(),
                accompany.getLongitude(),
                accompany.getTime(),
                accompany.getDetail()
            ))
            .peek(response -> log.info("Accompany details: {}", response))
            .toList();
    }

    public AccompanyResponse addTrackingAccompany(Long reservationId, AccompanyRequest accompanyRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime accompanyDateTime = LocalDateTime.parse(accompanyRequest.statusDate(), formatter);

        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReservationException(ReservationErrorCode.RESERVATION_NOT_FOUND));

        Accompany track = Accompany.builder()
            .accompanyStatus(accompanyRequest.status())
            .time(accompanyDateTime)
            .latitude(accompanyRequest.latitude())
            .longitude(accompanyRequest.longitude())
            .detail(accompanyRequest.statusDescribe())
            .build();

        accompanyRepository.save(track);
        reservation.addAccompany(track);
        reservationRepository.save(reservation);

        return new AccompanyResponse(
            track.getAccompanyStatus(),
            track.getLatitude(),
            track.getLongitude(),
            track.getTime(),
            track.getDetail());
    }
}
