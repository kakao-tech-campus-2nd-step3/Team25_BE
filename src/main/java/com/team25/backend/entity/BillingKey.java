package com.team25.backend.entity;

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

    private String bid; // 발급된 빌링키
    private String cardCode;
    private String cardName;
    private String userUuid; // 사용자 식별자 (예: 이메일, 아이디 등)
    private String orderId; // 상점 거래 고유번호 (빌링키 삭제 시 사용)
}
