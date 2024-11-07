package com.team25.backend.domain.user.entity;

import com.team25.backend.domain.manager.entity.Manager;
import com.team25.backend.domain.reservation.entity.Reservation;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", length = 100, nullable = false)
    private String username;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "role")
    private String role;

    // Manager와의 연관관계 추가
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Manager manager;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    public User(String username, String uuid, String role) {
        this.username = username;
        this.role = role;
        this.uuid = uuid;
    }

    public void updateRole(String role){
        this.role = role;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}