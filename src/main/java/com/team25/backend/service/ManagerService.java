package com.team25.backend.service;

import com.team25.backend.dto.response.ManagerResponse;
import com.team25.backend.entity.Manager;
import com.team25.backend.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;

    public List<ManagerResponse> getManagersByRegion(String region) {
        List<Manager> managers = managerRepository.findByWorkingRegion(region);

        return managers.stream()
            .map(manager -> ManagerResponse.builder()
                .managerId(manager.getManagerId())
                .name(manager.getManagerName())
                .profileImage(manager.getProfileImage())
                .career(manager.getCareer())
                .comment(manager.getComment())
                .build())
            .collect(Collectors.toList());
    }
}
