package com.team25.backend.domain.reservation.repository;

import com.team25.backend.domain.reservation.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}