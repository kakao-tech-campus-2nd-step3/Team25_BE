package com.team25.backend.repository;

import com.team25.backend.entity.WorkingHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkingHourRepository extends JpaRepository<WorkingHour, Long> {
    Optional<WorkingHour> findByManagerId(Long managerId);
}
