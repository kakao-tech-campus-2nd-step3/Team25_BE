package com.team25.backend.domain.manager.repository;

import com.team25.backend.domain.manager.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
