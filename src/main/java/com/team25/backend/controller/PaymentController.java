package com.team25.backend.controller;

import com.team25.backend.dto.request.BillingKeyRequest;
import com.team25.backend.dto.request.PaymentRequest;
import com.team25.backend.dto.request.ExpireBillingKeyRequest;
import com.team25.backend.dto.response.BillingKeyResponse;
import com.team25.backend.dto.response.PaymentResponse;
import com.team25.backend.dto.response.ExpireBillingKeyResponse;
import com.team25.backend.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // 빌링키 발급
    @PostMapping("/billing-key")
    public ResponseEntity<BillingKeyResponse> createBillingKey(@RequestBody BillingKeyRequest requestDto) throws Exception {
        String userId = getCurrentUserId(); // 현재 사용자 식별자 가져오기
        BillingKeyResponse responseDto = paymentService.createBillingKey(requestDto, userId);
        return ResponseEntity.ok(responseDto);
    }

    // 결제 요청
    @PostMapping("/payment")
    public ResponseEntity<PaymentResponse> payment(@RequestBody PaymentRequest requestDto) throws Exception {
        String userId = getCurrentUserId();
        PaymentResponse responseDto = paymentService.requestPayment(userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 빌링키 삭제
    @PostMapping("/billing-key/expire")
    public ResponseEntity<ExpireBillingKeyResponse> expireBillingKey(@RequestBody ExpireBillingKeyRequest requestDto) throws Exception {
        String userId = getCurrentUserId();
        ExpireBillingKeyResponse responseDto = paymentService.expireBillingKey(userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    private String getCurrentUserId() {
        // 인증된 사용자의 식별자를 반환해야 한다
        return "user@example.com";
    }
}
