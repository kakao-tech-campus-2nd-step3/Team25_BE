package com.team25.backend.repository;

import com.team25.backend.entity.Report;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>,
    JpaSpecificationExecutor<Report> {
    public Optional<List<Report>> findByReservation_Id(Long reservation);

}