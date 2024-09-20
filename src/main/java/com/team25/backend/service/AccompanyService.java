package com.team25.backend.service;

import com.team25.backend.dto.AccompanyDto;
import com.team25.backend.entity.Accompany;
import com.team25.backend.entity.Reservation;
import com.team25.backend.repository.AccompanyRepository;
import com.team25.backend.repository.ReservationRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AccompanyService {

    private final AccompanyRepository accompanyRepository;
    private final ReservationRepository reservationRepository;

    public AccompanyService(AccompanyRepository accompanyRepository,
        ReservationRepository reservationRepository) {
        this.accompanyRepository = accompanyRepository;
        this.reservationRepository = reservationRepository;
    }

    // 실시간 동행 정보 조회
    public List<Accompany> getTrackingAccompanies(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(IllegalArgumentException::new);
        return reservation.getAccompany();
    }

    // 실시간 동행 정보 생성
    public Accompany getTrackingAccompany(Long reservationId, AccompanyDto accompanyDto) {
        Accompany track = Accompany.builder().accompanyStatus(accompanyDto.accompanyStatus())
            .time(accompanyDto.time()).latitude(accompanyDto.latitude())
            .longitude(accompanyDto.longitude()).longitude(accompanyDto.longitude())
            .detail(accompanyDto.detail()).build();
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(IllegalArgumentException::new);
        reservation.getAccompany().add(track);
        reservationRepository.save(reservation);
        accompanyRepository.save(track);
        return track;
    }

}
