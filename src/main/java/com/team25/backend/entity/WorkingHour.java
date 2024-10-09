package com.team25.backend.entity;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class WorkingHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerId;

    private String monStartTime = "00:00";
    private String monEndTime = "00:00";
    private String tueStartTime = "00:00";
    private String tueEndTime = "00:00";
    private String wedStartTime = "00:00";
    private String wedEndTime = "00:00";
    private String thuStartTime = "00:00";
    private String thuEndTime = "00:00";
    private String friStartTime = "00:00";
    private String friEndTime = "00:00";
    private String satStartTime = "00:00";
    private String satEndTime = "00:00";
    private String sunStartTime = "00:00";
    private String sunEndTime = "00:00";

    @OneToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;
}
