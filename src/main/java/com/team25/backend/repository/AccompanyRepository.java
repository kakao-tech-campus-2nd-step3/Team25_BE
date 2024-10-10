package com.team25.backend.repository;

import com.team25.backend.entity.Accompany;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccompanyRepository extends JpaRepository<Accompany, Long>,
    JpaSpecificationExecutor<Accompany> {

    Optional<List<Accompany>> findByReservation_id(Long reservationId);
}