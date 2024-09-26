package com.team25.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team25.backend.enumdomain.CancelReason;
import com.team25.backend.enumdomain.ReservationStatus;
import com.team25.backend.enumdomain.ServiceType;
import com.team25.backend.enumdomain.Transportation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reservation")
@ToString
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;

    @Column(name = "departure_location", nullable = false) // ERD 컬럼 이름 변경 요망
    private String departureLocation; // 출발지

    @Column(name = "arrival_location", nullable = false) // ERD 컬럼 이름 변경 요망
    private String arrivalLocation; // 도착지

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
    @Column(name = "reservation_date", nullable = false) // ERD 컬럼 이름 변경 요망
    private LocalDateTime reservationDateTime; // 예약일시

    @Column(name = "created_time", nullable = false) // ERD 컬럼 이름 변경 요망
    private LocalDateTime createdTime; // 생성시간

    @Column(name = "payment_status", nullable = false)
    private boolean paymentStatus; // 결제 상태

    @Column(name = "reservation_status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ReservationStatus reservationStatus; // 예약 상태

    @Column(name = "cancel_reason")
    @Enumerated(value = EnumType.STRING)
    private  CancelReason cancelReason; // 취소 사유

    @Column(name = "cancel_detail")
    private  String cancelDetail; // 취소 세부 사유

    @Column(name = "creation_date")
    private LocalDateTime creationDate; // 생성일

    @Column(name = "service_type")
    @Enumerated(value = EnumType.STRING)
    private ServiceType serviceType; // 서비스 종류 (내시경검사, 외래 진료)

    @Column(name = "transportation")
    @Enumerated(value = EnumType.STRING)
    private Transportation transportation; // 이동 수단

    @Column(name = "price")
    private int price; // 비용

    @Column(name = "manager_status")
    private boolean mangerStatus; // 매니저 상태

    @OneToMany(mappedBy = "reservation")
    @Exclude
    private List<Report> reports = new ArrayList<>(); // 리포트

    @OneToMany(mappedBy = "reservation")
    @Exclude
    private List<Accompany> accompany = new ArrayList<>(); // 동행

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;
}