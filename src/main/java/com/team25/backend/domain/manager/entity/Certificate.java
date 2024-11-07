package com.team25.backend.domain.manager.entity;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "certificates")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificateId;

    private String certificateImage;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;
}
