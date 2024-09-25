package com.team25.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpireBillingKeyResponse {
    private String resultCode;
    private String resultMsg;
    private String tid;
    private String orderId;
    private String bid;
    private String authDate;
}
