package com.team25.backend.service;

import com.team25.backend.dto.request.ManagerCreateRequest;
import com.team25.backend.dto.request.ManagerWorkingHourRequest;
import com.team25.backend.dto.response.ManagerByDateAndRegionResponse;
import com.team25.backend.dto.response.ManagerCreateResponse;
import com.team25.backend.dto.response.ManagerProfileResponse;
import com.team25.backend.dto.response.ManagerWorkingHourResponse;
import com.team25.backend.entity.Manager;
import com.team25.backend.entity.Certificate;
import com.team25.backend.entity.WorkingHour;
import com.team25.backend.enumdomain.Day;
import com.team25.backend.exception.ManagerException;
import com.team25.backend.exception.ManagerErrorCode;
import com.team25.backend.repository.ManagerRepository;
import com.team25.backend.repository.CertificateRepository;
import com.team25.backend.repository.WorkingHourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final CertificateRepository certificateRepository;
    private final WorkingHourRepository workingHourRepository;

    public List<ManagerByDateAndRegionResponse> getManagersByDateAndRegion(String date, String region) {
        validateDate(date);
        validateRegion(region);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        Day day = Day.fromEnglishName(localDate.getDayOfWeek().toString());

        List<Manager> managers = managerRepository.findByWorkingHoursDayAndWorkingRegion(day, region);

        return managers.stream()
            .map(ManagerByDateAndRegionResponse::fromEntity)
            .collect(Collectors.toList());
    }

    private void validateDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new ManagerException(ManagerErrorCode.INVALID_DATE_FORMAT);
        }
    }

    private void validateRegion(String region) {
        if (!regionExists(region)) {
            throw new ManagerException(ManagerErrorCode.REGION_NOT_FOUND);
        }
    }

    private boolean regionExists(String region) {
        return List.of("Seoul", "Busan", "Daegu").contains(region);
    }

    public ManagerCreateResponse createManager(ManagerCreateRequest request) {
        validateCreateRequest(request);

        Manager manager = Manager.builder()
            .managerName(request.getName())
            .profileImage(request.getProfileImage())
            .career(request.getCareer())
            .comment(request.getComment())
            .build();

        managerRepository.save(manager);

        Certificate certificate = Certificate.builder()
            .certificateImage(request.getCertificateImage())
            .manager(manager)
            .build();

        certificateRepository.save(certificate);

        return ManagerCreateResponse.builder().build();
    }

    private void validateCreateRequest(ManagerCreateRequest request) {
        if (request.getName().isEmpty()) {
            throw new ManagerException(ManagerErrorCode.INVALID_INPUT_VALUE);
        }
    }

    public ManagerProfileResponse getManagerProfile(Long managerId) {
        Manager manager = managerRepository.findById(managerId)
            .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        return ManagerProfileResponse.fromEntity(manager);
    }

    public ManagerWorkingHourResponse addWorkingHour(Long managerId, ManagerWorkingHourRequest request) {
        Manager manager = managerRepository.findById(managerId)
            .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        validateWorkingHourRequest(request);

        WorkingHour workingHour = WorkingHour.builder()
            .day(Day.fromKoreanName(request.getDay()))
            .startTime(request.getStartTime())
            .endTime(request.getEndTime())
            .manager(manager)
            .build();

        workingHourRepository.save(workingHour);

        return ManagerWorkingHourResponse.builder().build();
    }

    private void validateWorkingHourRequest(ManagerWorkingHourRequest request) {
        if (!request.getStartTime().matches("\\d{2}:\\d{2}")) {
            throw new ManagerException(ManagerErrorCode.INVALID_WORKING_HOUR_FORMAT);
        }
        if (!request.getEndTime().matches("\\d{2}:\\d{2}")) {
            throw new ManagerException(ManagerErrorCode.INVALID_WORKING_HOUR_FORMAT);
        }

        try {
            Day.fromKoreanName(request.getDay());
        } catch (IllegalArgumentException e) {
            throw new ManagerException(ManagerErrorCode.INVALID_INPUT_VALUE);
        }
    }
}
