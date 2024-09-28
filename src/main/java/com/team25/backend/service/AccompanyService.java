package com.team25.backend.service;

import com.team25.backend.dto.request.AccompanyRequest;
import com.team25.backend.dto.response.AccompanyResponse;
import com.team25.backend.entity.Accompany;
import com.team25.backend.entity.Reservation;
import com.team25.backend.enumdomain.AccompanyStatus;
import com.team25.backend.repository.AccompanyRepository;
import com.team25.backend.repository.ReservationRepository;
import java.util.ArrayList;
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
    public List<AccompanyResponse> getTrackingAccompanies(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(IllegalArgumentException::new);
        List<Accompany> accompanies = reservation.getAccompany();
        List<AccompanyResponse> accompaniesResponse = new ArrayList<>();
        for (Accompany accompany : accompanies) {
            accompaniesResponse.add(new AccompanyResponse(
                accompany.getAccompanyStatus(), accompany.getLatitude(), accompany.getLongitude(),
                accompany.getTime(), accompany.getDetail()
            ));
        }
        return accompaniesResponse;
    }

    // 실시간 동행 정보 생성
    public AccompanyResponse addTrackingAccompany(Long reservationId,
        AccompanyRequest accompanyRequest) {
        AccompanyStatus accompanyStatus = AccompanyStatus.valueOf(
            accompanyRequest.accompanyStatus());
        Accompany track = Accompany.builder().accompanyStatus(accompanyStatus)
            .time(accompanyRequest.time()).latitude(accompanyRequest.latitude())
            .longitude(accompanyRequest.longitude()).longitude(accompanyRequest.longitude())
            .detail(accompanyRequest.detail()).build();
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(IllegalArgumentException::new);
        reservation.getAccompany().add(track);
        reservationRepository.save(reservation);
        accompanyRepository.save(track);
        return new AccompanyResponse(
            accompanyStatus,
            accompanyRequest.latitude(), accompanyRequest.longitude(), accompanyRequest.time(),
            accompanyRequest.detail());
    }

}
