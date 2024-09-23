package com.team25.backend.service;

import com.team25.backend.dto.response.ManagerByDateAndRegionResponse;
import com.team25.backend.entity.Manager;
import com.team25.backend.enumdomain.Day;
import com.team25.backend.exception.ManagerException;
import com.team25.backend.exception.ManagerErrorCode;
import com.team25.backend.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;

    public List<ManagerByDateAndRegionResponse> getManagersByDateAndRegion(String date, String region) {
        validateDate(date);
        validateRegion(region);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        Day day = convertToDayEnum(localDate.getDayOfWeek().toString());

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

    private Day convertToDayEnum(String dayOfWeek) {
        return switch (dayOfWeek.toUpperCase()) {
            case "MONDAY" -> Day.MONDAY;
            case "TUESDAY" -> Day.TUESDAY;
            case "WEDNESDAY" -> Day.WEDNESDAY;
            case "THURSDAY" -> Day.THURSDAY;
            case "FRIDAY" -> Day.FRIDAY;
            case "SATURDAY" -> Day.SATURDAY;
            case "SUNDAY" -> Day.SUNDAY;
            default -> throw new ManagerException(ManagerErrorCode.INVALID_INPUT_VALUE);
        };
    }
}
