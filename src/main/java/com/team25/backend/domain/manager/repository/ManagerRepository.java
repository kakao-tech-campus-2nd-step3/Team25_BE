package com.team25.backend.domain.manager.repository;

import com.team25.backend.domain.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
  List<Manager> findByWorkingRegion(String workingRegion);
  Optional<Manager> findByUserId(Long userId);
  @Query("SELECT m FROM Manager m WHERE m.workingHour IS NOT NULL AND size(m.certificates) > 0")
  List<Manager> findManagersWithCertificatesAndWorkingHour();
}