package com.team25.backend.service;

import com.team25.backend.dto.request.BillingKeyRequest;
import com.team25.backend.dto.request.PaymentCancelRequest;
import com.team25.backend.dto.request.PaymentRequest;
import com.team25.backend.dto.request.ExpireBillingKeyRequest;
import com.team25.backend.dto.response.BillingKeyResponse;
import com.team25.backend.dto.response.PaymentInfoResponse;
import com.team25.backend.dto.response.PaymentResponse;
import com.team25.backend.dto.response.ExpireBillingKeyResponse;
import com.team25.backend.entity.BillingKey;
import com.team25.backend.entity.Payment;
import com.team25.backend.entity.Reservation;
import com.team25.backend.entity.User;
import com.team25.backend.repository.BillingKeyRepository;
import com.team25.backend.repository.PaymentRepository;
import com.team25.backend.repository.ReservationRepository;
import com.team25.backend.repository.UserRepository;
import com.team25.backend.util.EncryptionUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final RestClient restClient;
    private final BillingKeyRepository billingKeyRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    private static final String CLIENT_KEY = System.getenv("NICEPAY_CLIENT_KEY");
    private static final String SECRET_KEY = System.getenv("NICEPAY_SECRET_KEY");

    public PaymentService(RestClient restClient, BillingKeyRepository billingKeyRepository, UserRepository userRepository, PaymentRepository paymentRepository, ReservationRepository reservationRepository) {
        this.restClient = restClient;
        this.billingKeyRepository = billingKeyRepository;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
    }

    // Authorization 헤더 생성
    private String getAuthorizationHeader() {
        String credentials = CLIENT_KEY + ":" + SECRET_KEY;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        return "Basic " + encodedCredentials;
    }

    // 요청 바디 생성
    private Map<String, Object> getRequestBody(Map<String, Object> customParams, String ediDate, String signData) {
        Map<String, Object> body = new HashMap<>(customParams);
        if (ediDate != null) {
            body.put("ediDate", ediDate);
        }
        if (signData != null) {
            body.put("signData", signData);
        }
        return body;
    }

    // Payment 엔티티를 PaymentInfoResponse로 변환
    private PaymentInfoResponse convertToPaymentInfoResponse(Payment payment) {
        return new PaymentInfoResponse(
                payment.getStatus(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getBalanceAmt(),
                payment.getPaidAt(),
                payment.getCancelledAt(),
                payment.getGoodsName(),
                payment.getPayMethod(),
                payment.getCardAlias(),
                payment.getTid(),
                payment.getReceiptUrl()
        );
    }

    // 빌링키 존재 여부 확인
    public boolean billingKeyExists(String userUuid) {
        return billingKeyRepository.findByUserUuid(userUuid).isPresent();
    }

    // 빌링키 발급
    public BillingKeyResponse createBillingKey(String userUuid, BillingKeyRequest requestDto) throws Exception {
        User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String encData = requestDto.encData();
        String orderId = generateOrderId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", getAuthorizationHeader());

        String ediDate = getEdiDate();
        String signData = EncryptionUtil.generateSignData(orderId, ediDate, SECRET_KEY);

        // 요청 바디 생성
        Map<String, Object> customParams = new HashMap<>();
        customParams.put("encData", encData);
        customParams.put("orderId", orderId);

        Map<String, Object> body = getRequestBody(customParams, ediDate, signData);

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

        if ("0000".equals(responseDto.resultCode())) {
            BillingKey billingKey = new BillingKey();
            billingKey.setUser(user);
            billingKey.setBid(responseDto.bid());
            billingKey.setCardCode(responseDto.cardCode());
            billingKey.setCardName(responseDto.cardName());
            billingKey.setOrderId(orderId);
            billingKey.setCardAlias(requestDto.cardAlias() != null ? requestDto.cardAlias() : responseDto.cardName());
            billingKeyRepository.save(billingKey);
        }

        return responseDto;
    }

    // 결제 요청
    public PaymentResponse requestPayment(String userUuid, PaymentRequest requestDto) throws Exception {
        BillingKey billingKey = billingKeyRepository.findByUserUuid(userUuid)
                .orElseThrow(() -> new RuntimeException("Billing key not found"));
        User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Reservation reservation = reservationRepository.findById(requestDto.reservationId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        String bid = billingKey.getBid();
        String orderId = generateOrderId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", getAuthorizationHeader());

        String ediDate = getEdiDate();
        String signData = EncryptionUtil.generateSignData(orderId, bid, ediDate, SECRET_KEY);

        // 요청 바디 생성
        Map<String, Object> customParams = new HashMap<>();
        customParams.put("orderId", orderId);
        customParams.put("amount", requestDto.amount());
        customParams.put("goodsName", requestDto.goodsName());
        customParams.put("cardQuota", requestDto.cardQuota());
        customParams.put("useShopInterest", requestDto.useShopInterest());

        Map<String, Object> body = getRequestBody(customParams, ediDate, signData);

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

        if ("0000".equals(responseDto.resultCode())) {
            Payment payment = new Payment();
            payment.setUser(user);
            payment.setReservation(reservation);
            payment.setStatus(responseDto.status());
            payment.setOrderId(responseDto.orderId());
            payment.setAmount(responseDto.amount());
            payment.setBalanceAmt(responseDto.balanceAmt());
            payment.setPaidAt(responseDto.paidAt());
            payment.setCancelledAt(responseDto.cancelledAt());
            payment.setGoodsName(responseDto.goodsName());
            payment.setPayMethod(responseDto.payMethod());
            payment.setCardAlias(billingKey.getCardAlias());
            payment.setTid(responseDto.tid());
            payment.setReceiptUrl(responseDto.receiptUrl());
            paymentRepository.save(payment);
        }

        return responseDto;
    }

    // 결제 취소
    public PaymentResponse requestCancel(String userUuid, PaymentCancelRequest requestDto) throws Exception {
        Payment payment = paymentRepository.findByOrderId(requestDto.orderId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        if (!Objects.equals(userUuid, payment.getUser().getUuid())) {
            throw new RuntimeException("Payment user does not belong to this order");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", getAuthorizationHeader());

        // 요청 바디 생성
        Map<String, Object> customParams = new HashMap<>();
        customParams.put("reason", requestDto.reason());
        customParams.put("orderId", requestDto.orderId());

        // 취소 요청에서는 ediDate와 signData가 필요 없으므로 null 전달
        Map<String, Object> body = getRequestBody(customParams, null, null);

        String url = "https://sandbox-api.nicepay.co.kr/v1/payments/" + payment.getTid() + "/cancel";

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

        if ("0000".equals(responseDto.resultCode())) {
            payment.setStatus(responseDto.status());
            payment.setAmount(responseDto.amount());
            payment.setBalanceAmt(responseDto.balanceAmt());
            payment.setPaidAt(responseDto.paidAt());
            payment.setCancelledAt(responseDto.cancelledAt());
            payment.setReceiptUrl(responseDto.receiptUrl());
            paymentRepository.save(payment);
        }

        return responseDto;
    }

    // 빌링키 삭제
    public ExpireBillingKeyResponse expireBillingKey(String userUuid, ExpireBillingKeyRequest requestDto) throws Exception {
        BillingKey billingKey = billingKeyRepository.findByUserUuid(userUuid)
                .orElseThrow(() -> new RuntimeException("Billing key not found"));

        String bid = billingKey.getBid();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", getAuthorizationHeader());

        String ediDate = getEdiDate();
        String signData = EncryptionUtil.generateSignData(requestDto.orderId(), bid, ediDate, SECRET_KEY);

        // 요청 바디 생성
        Map<String, Object> customParams = new HashMap<>();
        customParams.put("orderId", requestDto.orderId());

        Map<String, Object> body = getRequestBody(customParams, ediDate, signData);

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

    // 단일 결제정보 조회
    public PaymentInfoResponse getPaymentByOrderId(String orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return convertToPaymentInfoResponse(payment);
    }

    // 해당 유저의 결제정보 목록 조회
    public List<PaymentInfoResponse> getPaymentsByUserUuid(String userUuid) {
        User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Payment> payments = paymentRepository.findByUser(user);
        return payments.stream()
                .map(this::convertToPaymentInfoResponse)
                .collect(Collectors.toList());
    }

    // 해당 예약의 결제정보 목록 조회
    public List<PaymentInfoResponse> getPaymentsByReservationId(Long reservationId) {
        List<Payment> payments = paymentRepository.findByReservationId(reservationId);
        return payments.stream()
                .map(this::convertToPaymentInfoResponse)
                .collect(Collectors.toList());
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
