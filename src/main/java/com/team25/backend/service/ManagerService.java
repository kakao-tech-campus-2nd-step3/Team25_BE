package com.team25.backend.service;

import com.team25.backend.dto.request.*;
import com.team25.backend.dto.response.*;
import com.team25.backend.entity.Manager;
import com.team25.backend.entity.Certificate;
import com.team25.backend.entity.User;
import com.team25.backend.entity.WorkingHour;
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
        String dayOfWeek = localDate.getDayOfWeek().toString().toLowerCase();

        List<Manager> managers = managerRepository.findByWorkingRegion(region).stream()
            .filter(manager -> hasWorkingHoursOnDay(manager.getWorkingHour(), dayOfWeek))
            .toList();

        return managers.stream()
            .map(ManagerByDateAndRegionResponse::fromEntity)
            .collect(Collectors.toList());
    }

    private boolean hasWorkingHoursOnDay(WorkingHour workingHour, String dayOfWeek) {
        return switch (dayOfWeek) {
            case "monday" ->
                !("00:00".equals(workingHour.getMonStartTime()) && "00:00".equals(workingHour.getMonEndTime()));
            case "tuesday" ->
                !("00:00".equals(workingHour.getTueStartTime()) && "00:00".equals(workingHour.getTueEndTime()));
            case "wednesday" ->
                !("00:00".equals(workingHour.getWedStartTime()) && "00:00".equals(workingHour.getWedEndTime()));
            case "thursday" ->
                !("00:00".equals(workingHour.getThuStartTime()) && "00:00".equals(workingHour.getThuEndTime()));
            case "friday" ->
                !("00:00".equals(workingHour.getFriStartTime()) && "00:00".equals(workingHour.getFriEndTime()));
            case "saturday" ->
                !("00:00".equals(workingHour.getSatStartTime()) && "00:00".equals(workingHour.getSatEndTime()));
            case "sunday" ->
                !("00:00".equals(workingHour.getSunStartTime()) && "00:00".equals(workingHour.getSunEndTime()));
            default -> false;
        };
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
        return List.of("Busan", "Seoul",
            "부산광역시 중구", "부산광역시 서구", "부산광역시 동구", "부산광역시 영도구",
            "부산광역시 부산진구", "부산광역시 동래구", "부산광역시 남구", "부산광역시 북구",
            "부산광역시 해운대구", "부산광역시 사하구", "부산광역시 금정구", "부산광역시 강서구",
            "부산광역시 연제구", "부산광역시 수영구", "부산광역시 사상구", "부산광역시 기장군").contains(region);
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
            .gender(request.gender())
            .isRegistered(false)
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

    public ManagerWorkingHourUpdateResponse updateWorkingHour(Long managerId, ManagerWorkingHourUpdateRequest request) {
        Manager manager = managerRepository.findById(managerId)
            .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        WorkingHour workingHour = workingHourRepository.findByManagerId(managerId)
            .orElseThrow(() -> new ManagerException(ManagerErrorCode.WORKING_HOUR_NOT_FOUND));

        validateWorkingHourRequest(request);

        workingHour.setMonStartTime(request.monStartTime());
        workingHour.setMonEndTime(request.monEndTime());
        workingHour.setTueStartTime(request.tueStartTime());
        workingHour.setTueEndTime(request.tueEndTime());
        workingHour.setWedStartTime(request.wedStartTime());
        workingHour.setWedEndTime(request.wedEndTime());
        workingHour.setThuStartTime(request.thuStartTime());
        workingHour.setThuEndTime(request.thuEndTime());
        workingHour.setFriStartTime(request.friStartTime());
        workingHour.setFriEndTime(request.friEndTime());
        workingHour.setSatStartTime(request.satStartTime());
        workingHour.setSatEndTime(request.satEndTime());
        workingHour.setSunStartTime(request.sunStartTime());
        workingHour.setSunEndTime(request.sunEndTime());

        workingHourRepository.save(workingHour);

        return ManagerWorkingHourUpdateResponse.fromEntity(workingHour);
    }

    private void validateWorkingHourRequest(ManagerWorkingHourUpdateRequest request) {
        validateWorkingHour(request.monStartTime(), request.monEndTime());
        validateWorkingHour(request.tueStartTime(), request.tueEndTime());
        validateWorkingHour(request.wedStartTime(), request.wedEndTime());
        validateWorkingHour(request.thuStartTime(), request.thuEndTime());
        validateWorkingHour(request.friStartTime(), request.friEndTime());
        validateWorkingHour(request.satStartTime(), request.satEndTime());
        validateWorkingHour(request.sunStartTime(), request.sunEndTime());
    }
}
