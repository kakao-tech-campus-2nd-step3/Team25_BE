package com.team25.backend.repository;

import com.team25.backend.entity.TimeOfDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeOfDaysRepository extends JpaRepository<TimeOfDays, Long>,
    JpaSpecificationExecutor<TimeOfDays> {

}