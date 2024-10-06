package com.team25.backend.repository;

import com.team25.backend.entity.WorkingHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingHourRepository extends JpaRepository<WorkingHour, Long> {
}
