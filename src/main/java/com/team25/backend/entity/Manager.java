package com.team25.backend.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "manager_id", referencedColumnName = "user_id")
public class Manager extends User{
    @Column(length = 50)
    private String managerName;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificate> certificates;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkingHour> workingHours;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "profile_image", nullable = false)
    private String profileImage;

    @Column(name = "carrer", nullable = false, length = 256)
    private String career;

    @Column(name = "comment", nullable = false, length = 256)
    private String comment;

    @Column(name = "working_region", nullable = false, length = 256)
    private String workingRegion;

    @OneToMany(mappedBy = "manager")
    private List<Reservation> reservations = new ArrayList<>();
}
