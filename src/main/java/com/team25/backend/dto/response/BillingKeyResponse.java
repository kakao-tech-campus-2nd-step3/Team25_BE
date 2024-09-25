package com.team25.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingKeyResponse {
    private String resultCode;
    private String resultMsg;
    private String bid;
    private String authDate;
    private String cardCode;
    private String cardName;
    private String tid;
}
