package com.team25.backend.domain.auth.repository;

import com.team25.backend.domain.auth.entity.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshRepository extends JpaRepository<Refresh, Long> {
    Boolean existsByRefresh(String refresh);
    @Transactional
    void deleteByRefresh(String refresh);
}
