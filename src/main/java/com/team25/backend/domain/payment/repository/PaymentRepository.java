package com.team25.backend.domain.payment.repository;

import com.team25.backend.domain.payment.entity.Payment;
import com.team25.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(String orderId);
    List<Payment> findByUser(User user);
    List<Payment> findByReservationId(Long reservationId);
}
