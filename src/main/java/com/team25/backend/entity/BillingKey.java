package com.team25.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "billing_keys")
public class BillingKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bid; // 발급된 빌링키
    private String cardCode;
    private String cardName;
    private String userId; // 사용자 식별자 (예: 이메일, 아이디 등)

    public Long getId() {
        return id;
    }

    // ID는 자동 생성되므로 Setter는 만들지 않음

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
