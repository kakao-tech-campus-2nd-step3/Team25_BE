package com.team25.backend.domain.manager.entity;

import com.team25.backend.domain.manager.entity.Manager;
import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "working_hour")
public class WorkingHour {

    @Id
    private Long managerId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "manager_id")
    private Manager manager;

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
}
