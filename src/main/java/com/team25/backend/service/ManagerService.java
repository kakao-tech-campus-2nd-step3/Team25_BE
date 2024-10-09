package com.team25.backend.service;

import com.team25.backend.dto.request.*;
import com.team25.backend.dto.response.*;
import com.team25.backend.entity.Manager;
import com.team25.backend.entity.Certificate;
import com.team25.backend.entity.User;
import com.team25.backend.entity.WorkingHour;
import com.team25.backend.enumdomain.Day;
import com.team25.backend.exception.ManagerException;
import com.team25.backend.exception.ManagerErrorCode;
import com.team25.backend.repository.ManagerRepository;
import com.team25.backend.repository.CertificateRepository;
import com.team25.backend.repository.WorkingHourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
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

        List<Manager> managers = managerRepository.findByWorkingHoursDayOfWeekAndWorkingRegion(day, region);

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

    @Transactional
    public ManagerCreateResponse createManager(User user, ManagerCreateRequest request) {

        if (user.getId() == null) {
            throw new IllegalArgumentException("User ID가 null입니다. 이미 존재하는 User를 사용해야 합니다.");
        }

        validateCreateRequest(request);

        Manager manager = Manager.builder()
                .user(user)
                .managerName(request.name())
                .profileImage(request.profileImage())
                .career(request.career())
                .comment(request.comment())
                .build();

        managerRepository.save(manager);

        Certificate certificate = Certificate.builder()
                .certificateImage(request.certificateImage())
                .manager(manager)
                .build();

        certificateRepository.save(certificate);

        return new ManagerCreateResponse();
    }

    private void validateCreateRequest(ManagerCreateRequest request) {
        if (request.name().isEmpty()) {
            throw new ManagerException(ManagerErrorCode.INVALID_INPUT_VALUE);
        }
    }

    public ManagerProfileResponse getManagerProfile(Long managerId) {
        Manager manager = managerRepository.findById(managerId)
            .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        return ManagerProfileResponse.fromEntity(manager);
    }

    public ManagerWorkingHourCreateResponse addWorkingHour(Long managerId, ManagerWorkingHourCreateRequest request) {
        Manager manager = managerRepository.findById(managerId)
            .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        validateWorkingHourRequest(request);

        WorkingHour workingHour = WorkingHour.builder()
            .manager(manager)
            .monStartTime(request.monStartTime())
            .monEndTime(request.monEndTime())
            .tueStartTime(request.tueStartTime())
            .tueEndTime(request.tueEndTime())
            .wedStartTime(request.wedStartTime())
            .wedEndTime(request.wedEndTime())
            .thuStartTime(request.thuStartTime())
            .thuEndTime(request.thuEndTime())
            .friStartTime(request.friStartTime())
            .friEndTime(request.friEndTime())
            .satStartTime(request.satStartTime())
            .satEndTime(request.satEndTime())
            .sunStartTime(request.sunStartTime())
            .sunEndTime(request.sunEndTime())
            .build();

        workingHourRepository.save(workingHour);

        return new ManagerWorkingHourCreateResponse();
    }

    private void validateWorkingHourRequest(ManagerWorkingHourCreateRequest request) {
        validateWorkingHour(request.monStartTime(), request.monEndTime());
        validateWorkingHour(request.tueStartTime(), request.tueEndTime());
        validateWorkingHour(request.wedStartTime(), request.wedEndTime());
        validateWorkingHour(request.thuStartTime(), request.thuEndTime());
        validateWorkingHour(request.friStartTime(), request.friEndTime());
        validateWorkingHour(request.satStartTime(), request.satEndTime());
        validateWorkingHour(request.sunStartTime(), request.sunEndTime());
    }

    private void validateWorkingHour(String startTime, String endTime) {
        if (!startTime.matches("\\d{2}:\\d{2}")) {
            throw new ManagerException(ManagerErrorCode.INVALID_WORKING_HOUR_FORMAT);
        }
        if (!endTime.matches("\\d{2}:\\d{2}")) {
            throw new ManagerException(ManagerErrorCode.INVALID_WORKING_HOUR_FORMAT);
        }
    }


    public ManagerProfileImageUpdateResponse updateProfileImage(Long managerId, ManagerProfileImageUpdateRequest request) {
        Manager manager = managerRepository.findById(managerId)
            .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        validateProfileImage(request.profileImage());

        manager.setProfileImage(request.profileImage());
        managerRepository.save(manager);

        return ManagerProfileImageUpdateResponse.fromEntity(manager);
    }

    private void validateProfileImage(String profileImage) {
        if (profileImage == null || profileImage.isEmpty()) {
            throw new ManagerException(ManagerErrorCode.INVALID_PROFILE_IMAGE);
        }
    }

    public ManagerCommentUpdateResponse updateComment(Long managerId, ManagerCommentUpdateRequest request) {
        Manager manager = managerRepository.findById(managerId)
            .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        validateComment(request.comment());

        manager.setComment(request.comment());
        managerRepository.save(manager);

        return ManagerCommentUpdateResponse.fromEntity(manager);
    }

    private void validateComment(String comment) {
        if (comment == null || comment.isEmpty()) {
            throw new ManagerException(ManagerErrorCode.INVALID_COMMENT);
        }
    }

    public ManagerLocationUpdateResponse updateLocation(Long managerId, ManagerLocationUpdateRequest request) {
        Manager manager = managerRepository.findById(managerId)
            .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        validateWorkingRegion(request.workingRegion());

        manager.setWorkingRegion(request.workingRegion());
        managerRepository.save(manager);

        return ManagerLocationUpdateResponse.fromEntity(manager);
    }

    private void validateWorkingRegion(String workingRegion) {
        if (workingRegion == null || workingRegion.isEmpty()) {
            throw new ManagerException(ManagerErrorCode.INVALID_WORKING_REGION);
        }
    }

    public ManagerWorkingHourUpdateResponse updateWorkingHour(Long managerId, Long workingHoursId, ManagerWorkingHourUpdateRequest request) {
        Manager manager = managerRepository.findById(managerId)
            .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        WorkingHour workingHour = workingHourRepository.findById(workingHoursId)
            .orElseThrow(() -> new ManagerException(ManagerErrorCode.WORKING_HOUR_NOT_FOUND));

        if (!workingHour.getManager().getId().equals(managerId)) {
            throw new ManagerException(ManagerErrorCode.INVALID_INPUT_VALUE);
        }

        validateWorkingHourRequest(request);

        workingHour.setDayOfWeek(Day.fromKoreanName(request.day()));
        workingHour.setStartTime(request.startTime());
        workingHour.setEndTime(request.endTime());
        workingHourRepository.save(workingHour);

        return ManagerWorkingHourUpdateResponse.fromEntity(workingHour);
    }


    private void validateWorkingHourRequest(ManagerWorkingHourUpdateRequest request) {
        validateWorkingHour(request.startTime(), request.endTime(), request.day());
    }

    public ManagerWorkingHourDeleteResponse deleteWorkingHour(Long managerId, Long workingHoursId) {
        Manager manager = managerRepository.findById(managerId)
            .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        WorkingHour workingHour = workingHourRepository.findById(workingHoursId)
            .orElseThrow(() -> new ManagerException(ManagerErrorCode.WORKING_HOUR_NOT_FOUND));

        if (!workingHour.getManager().getId().equals(managerId)) {
            throw new ManagerException(ManagerErrorCode.INVALID_INPUT_VALUE);
        }

        workingHourRepository.delete(workingHour);

        return new ManagerWorkingHourDeleteResponse();
    }
}
