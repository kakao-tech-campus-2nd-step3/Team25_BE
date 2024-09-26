package com.team25.backend.service;

import com.team25.backend.dto.request.BillingKeyRequest;
import com.team25.backend.dto.request.PaymentRequest;
import com.team25.backend.dto.request.ExpireBillingKeyRequest;
import com.team25.backend.dto.response.BillingKeyResponse;
import com.team25.backend.dto.response.PaymentResponse;
import com.team25.backend.dto.response.ExpireBillingKeyResponse;
import com.team25.backend.entity.BillingKey;
import com.team25.backend.repository.BillingKeyRepository;
import com.team25.backend.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentService {

    private final RestClient restClient;
    private final BillingKeyRepository billingKeyRepository;

    @Value("${nicepay.clientKey}")
    private String clientKey;

    @Value("${nicepay.secretKey}")
    private String secretKey;

    public PaymentService(RestClient restClient, BillingKeyRepository billingKeyRepository) {
        this.restClient = restClient;
        this.billingKeyRepository = billingKeyRepository;
    }

    // Authorization 헤더 생성
    private String getAuthorizationHeader() {
        String credentials = clientKey + ":" + secretKey;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        return "Basic " + encodedCredentials;
    }

    // 빌링키 존재 여부 확인
    public boolean billingKeyExists(String userId) {
        return billingKeyRepository.findByUserId(userId).isPresent();
    }

    // 빌링키 발급
    public BillingKeyResponse createBillingKey(BillingKeyRequest requestDto, String userId) throws Exception {
        String encData = requestDto.getEncData();
        String orderId = generateOrderId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", getAuthorizationHeader());

        String ediDate = getEdiDate();
        String signData = EncryptionUtil.generateSignData(orderId, ediDate, secretKey);

        // 요청 바디 생성
        Map<String, Object> body = new HashMap<>();
        body.put("encData", encData);
        body.put("orderId", orderId);
        body.put("ediDate", ediDate);
        body.put("signData", signData);

        String url = "https://sandbox-api.nicepay.co.kr/v1/subscribe/regist";

        // 요청 및 응답 처리
        BillingKeyResponse responseDto;
        try {
            responseDto = restClient.post()
                    .uri(url)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .body(body)
                    .retrieve()
                    .body(BillingKeyResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to request billing key", e);
        }

        if ("0000".equals(responseDto.getResultCode())) {
            BillingKey billingKey = new BillingKey();
            billingKey.setBid(responseDto.getBid());
            billingKey.setCardCode(responseDto.getCardCode());
            billingKey.setCardName(responseDto.getCardName());
            billingKey.setUserId(userId);
            billingKey.setOrderId(orderId);
            billingKeyRepository.save(billingKey);
        }

        return responseDto;
    }

    // 결제 요청
    public PaymentResponse requestPayment(String userId, PaymentRequest requestDto) throws Exception {
        BillingKey billingKey = billingKeyRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Billing key not found"));

        String bid = billingKey.getBid();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", getAuthorizationHeader());

        String ediDate = getEdiDate();
        String signData = EncryptionUtil.generateSignData(requestDto.getOrderId(), bid, ediDate, secretKey);

        // 요청 바디 생성
        Map<String, Object> body = new HashMap<>();
        body.put("orderId", requestDto.getOrderId());
        body.put("amount", requestDto.getAmount());
        body.put("goodsName", requestDto.getGoodsName());
        body.put("cardQuota", requestDto.getCardQuota());
        body.put("useShopInterest", requestDto.isUseShopInterest());
        body.put("ediDate", ediDate);
        body.put("signData", signData);

        String url = "https://sandbox-api.nicepay.co.kr/v1/subscribe/" + bid + "/payments";

        // 요청 및 응답 처리
        PaymentResponse responseDto;
        try {
            responseDto = restClient.post()
                    .uri(url)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .body(body)
                    .retrieve()
                    .body(PaymentResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process payment", e);
        }

        return responseDto;
    }

    // 빌링키 삭제
    public ExpireBillingKeyResponse expireBillingKey(String userId, ExpireBillingKeyRequest requestDto) throws Exception {
        BillingKey billingKey = billingKeyRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Billing key not found"));

        String bid = billingKey.getBid();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", getAuthorizationHeader());

        String ediDate = getEdiDate();
        String signData = EncryptionUtil.generateSignData(requestDto.getOrderId(), bid, ediDate, secretKey);

        // 요청 바디 생성
        Map<String, Object> body = new HashMap<>();
        body.put("orderId", requestDto.getOrderId());
        body.put("ediDate", ediDate);
        body.put("signData", signData);

        String url = "https://sandbox-api.nicepay.co.kr/v1/subscribe/" + bid + "/expire";

        // 요청 및 응답 처리
        ExpireBillingKeyResponse responseDto;
        try {
            responseDto = restClient.post()
                    .uri(url)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .body(body)
                    .retrieve()
                    .body(ExpireBillingKeyResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to expire billing key", e);
        }

        // 빌링키 삭제 처리
        billingKeyRepository.delete(billingKey);

        return responseDto;
    }

    // 유틸리티 메서드들
    private String generateOrderId() {
        return UUID.randomUUID().toString();
    }

    private String getEdiDate() {
        // ISO 8601 형식으로 현재 시간 반환
        return java.time.ZonedDateTime.now().toString();
    }
}

