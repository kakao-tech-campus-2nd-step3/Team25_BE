package com.team25.backend.repository;

import com.team25.backend.entity.Manager;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

  List<Manager> findByWorkingRegion(String region);
}