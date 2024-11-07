package com.team25.backend.domain.report.repository;

import com.team25.backend.domain.report.entity.Report;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>,
    JpaSpecificationExecutor<Report> {
    List<Report> findByReservation_Id(Long reservation);
}