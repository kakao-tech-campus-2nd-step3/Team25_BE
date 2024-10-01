package com.team25.backend.repository;

import com.team25.backend.entity.Manager;
import com.team25.backend.enumdomain.Day;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
  List<Manager> findByWorkingHoursDayAndWorkingRegion(Day workingHours_day, String workingRegion);
}