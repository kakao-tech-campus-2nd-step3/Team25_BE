package com.team25.backend.domain.payment.repository;

import com.team25.backend.domain.payment.entity.BillingKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillingKeyRepository extends JpaRepository<BillingKey, Long> {
    Optional<BillingKey> findByUserUuid(String userUuid);
}

