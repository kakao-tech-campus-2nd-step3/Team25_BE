package com.team25.backend.domain.accompany.repository;

import com.team25.backend.domain.accompany.entity.Accompany;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccompanyRepository extends JpaRepository<Accompany, Long>,
    JpaSpecificationExecutor<Accompany> {
    List<Accompany> findByReservation_id(Long reservationId);
}