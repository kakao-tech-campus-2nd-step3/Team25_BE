package com.team25.backend.domain.user.repository;

import com.team25.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUuid(String uuid);
    Boolean existsByUsername(String username);

    // Manager가 있는 User만 조회하는 JPQL 쿼리
    @Query("SELECT u FROM User u WHERE u.manager IS NOT NULL")
    List<User> findUsersWithManager();
}