package com.team25.backend.entity;

import jakarta.persistence.*;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "manager_id", referencedColumnName = "user_id")
public class Manager extends User {
    @Column(length = 50)
    private String managerName;

    private String profileImage;
    private String career;
    private String comment;
    private String workingRegion;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificate> certificates = new ArrayList<>();

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkingHour> workingHours = new ArrayList<>();

    @OneToMany(mappedBy = "manager") // Reservation 관련은 삭제되면 안됨
    private List<Reservation> reservations = new ArrayList<>();
}
