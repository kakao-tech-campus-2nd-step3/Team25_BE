package com.team25.backend.repository;

import com.team25.backend.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    List<Manager> findByWorkingRegion(String workingRegion);
}
