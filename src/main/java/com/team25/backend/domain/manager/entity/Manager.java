package com.team25.backend.domain.manager.entity;

import com.team25.backend.domain.user.entity.User;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "managers")
public class Manager{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 50)
    private String managerName;

    private String profileImage;
    private String career;
    private String comment;
    private String workingRegion;
    private String gender; // 남성 or 여성
    private boolean isRegistered = false;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificate> certificates = new ArrayList<>();

    @OneToOne(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    private WorkingHour workingHour;
}
