package com.team25.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingKeyRequest {
    private String cardNo;
    private String expYear;
    private String expMonth;
    private String idNo;
    private String cardPw;
}
