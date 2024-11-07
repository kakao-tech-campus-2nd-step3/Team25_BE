package com.team25.backend.domain.accompany.entity;

import com.team25.backend.domain.reservation.entity.Reservation;
import com.team25.backend.domain.accompany.enumdomain.AccompanyStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accompanies")
public class Accompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // @OneToOne(mappedBy = "accompany")
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private AccompanyStatus accompanyStatus;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "detail")
    private String detail;

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        if (reservation != null) {
            reservation.getAccompany().add(this);
        }
    }

}