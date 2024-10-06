package com.team25.backend.controller;

import com.team25.backend.annotation.LoginUser;
import com.team25.backend.dto.request.BillingKeyRequest;
import com.team25.backend.dto.request.PaymentCancelRequest;
import com.team25.backend.dto.request.PaymentRequest;
import com.team25.backend.dto.request.ExpireBillingKeyRequest;
import com.team25.backend.dto.response.ApiResponse;
import com.team25.backend.dto.response.BillingKeyResponse;
import com.team25.backend.dto.response.PaymentResponse;
import com.team25.backend.dto.response.ExpireBillingKeyResponse;
import com.team25.backend.entity.User;
import com.team25.backend.service.PaymentService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse<BillingKeyResponse>> createBillingKey(@LoginUser User user, @RequestBody BillingKeyRequest requestDto) throws Exception {
        String userUuid = user.getUuid(); // 현재 사용자 식별자 가져오기
        BillingKeyResponse responseDto = paymentService.createBillingKey(userUuid, requestDto);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "빌링키 발급을 성공했습니다.", responseDto), HttpStatus.OK
        );
    }

    // 결제 요청
    @PostMapping("/payment")
    public ResponseEntity<ApiResponse<PaymentResponse>> payment(@LoginUser User user, @RequestBody PaymentRequest requestDto) throws Exception {
        String userUuid = user.getUuid();
        PaymentResponse responseDto = paymentService.requestPayment(userUuid, requestDto);
        return new ResponseEntity<>(
                new ApiResponse<>(true, responseDto.resultMsg(), responseDto), HttpStatus.OK
        );
    }

    // 결제 취소
    @PostMapping("/cancel")
    public ResponseEntity<ApiResponse<PaymentResponse>> cancel(@LoginUser User user, @RequestBody PaymentCancelRequest requestDto) throws Exception {
        String userUuid = user.getUuid();
        PaymentResponse responseDto = paymentService.requestCancel(userUuid, requestDto);
        return new ResponseEntity<>(
                new ApiResponse<>(true, responseDto.resultMsg(), responseDto), HttpStatus.OK
        );
    }

    // 빌링키 삭제
    @PostMapping("/billing-key/expire")
    public ResponseEntity<ApiResponse<ExpireBillingKeyResponse>> expireBillingKey(@LoginUser User user, @RequestBody ExpireBillingKeyRequest requestDto) throws Exception {
        String userUuid = user.getUuid();
        ExpireBillingKeyResponse responseDto = paymentService.expireBillingKey(userUuid, requestDto);
        return new ResponseEntity<>(
                new ApiResponse<>(true, responseDto.resultMsg(), responseDto), HttpStatus.OK
        );
    }

    // 빌링키 존재 유무 확인
    @GetMapping("/billing-key/exists")
    public ResponseEntity<ApiResponse<Boolean>> billingKeyExists(@LoginUser User user) {
        String userUuid = user.getUuid();
        boolean exists = paymentService.billingKeyExists(userUuid);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "성공적으로 빌링키 존재 유무를 가져왔습니다.", exists), HttpStatus.OK
        );
    }
}
