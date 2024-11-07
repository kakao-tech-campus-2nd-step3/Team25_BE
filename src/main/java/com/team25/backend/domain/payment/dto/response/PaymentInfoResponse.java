package com.team25.backend.domain.payment.dto.response;

public record PaymentInfoResponse(
        String status, // 결제 처리상태
        String orderId, // 주문 번호
        int amount, // 거래 금액
        int balanceAmt, // 취소 가능 잔액
        String paidAt, // 결제 완료 시점
        String cancelledAt, // 결제 취소 시점
        String goodsName, // 상품 명
        String payMethod, // 결제 수단
        String cardAlias, // 카드 별칭
        String tid, // 결제 승인 키
        String receiptUrl // 매출전표 확인 URL
) {
}
