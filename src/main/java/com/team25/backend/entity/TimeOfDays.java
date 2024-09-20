package com.team25.backend.entity;

import com.team25.backend.enumdomain.TimeEnum;
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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "time_of_days")
public class TimeOfDays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "time_of_day")
    @Enumerated(value = EnumType.STRING)
    private TimeEnum timeOfDay;

    @ManyToOne
    @JoinColumn(name = "REPORT_ID", nullable = false)
    private Report report;
}