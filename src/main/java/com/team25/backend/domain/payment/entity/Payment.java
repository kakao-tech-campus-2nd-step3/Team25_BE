package com.team25.backend.domain.payment.entity;

import com.team25.backend.domain.reservation.entity.Reservation;
import com.team25.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 사용자와의 연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    // 예약과의 연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private Reservation reservation;

    private String status; // 결제 처리상태
    private String orderId; // 주문 번호
    private int amount; // 거래 금액
    private int balanceAmt; // 취소 가능 잔액
    private String paidAt; // 결제 완료 시점
    private String cancelledAt; // 결제 취소 시점
    private String goodsName; // 상품 명
    private String payMethod; // 결제 수단
    private String cardAlias; // 카드 별칭
    private String tid; // 결제 승인 키
    private String receiptUrl; // 매출전표 확인 URL
}
