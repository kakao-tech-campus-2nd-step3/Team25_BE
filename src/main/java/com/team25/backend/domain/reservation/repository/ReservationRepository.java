package com.team25.backend.domain.reservation.repository;

import com.team25.backend.domain.reservation.entity.Reservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>,
    JpaSpecificationExecutor<Reservation> {
    List<Reservation> findByUser_Uuid(String userUuid);
}