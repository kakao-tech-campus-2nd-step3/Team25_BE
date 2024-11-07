package com.team25.backend.domain.payment.entity;

import com.team25.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "billing_keys")
public class BillingKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자와의 연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    private String bid; // 암호화 된 빌링키
    private String cardCode;
    private String cardName;
    private String orderId; // 상점 거래 고유번호 (빌링키 삭제 시 사용)
    private String cardAlias; // 카드 별칭
}
