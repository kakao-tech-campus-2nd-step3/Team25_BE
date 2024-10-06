package com.team25.backend.entity;

import lombok.*;
import jakarta.persistence.*;
import com.team25.backend.enumdomain.Day;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class WorkingHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workingHourId;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private Day dayOfWeek;

    private String startTime;
    private String endTime;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;
}
