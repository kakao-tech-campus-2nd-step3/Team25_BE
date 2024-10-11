package com.team25.backend.service;

import com.team25.backend.dto.request.PatientRequest;
import com.team25.backend.entity.Patient;
import com.team25.backend.repository.PatientRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // 환자 정보 생성
    public Patient addPatient(PatientRequest patientRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate patientBirthday =
            LocalDate.parse(patientRequest.birthDate(), formatter);

        Patient newPatient = Patient.builder()
            .name(patientRequest.name())
            .phoneNumber(getParsedNumber(patientRequest.phoneNumber()))
            .gender(patientRequest.patientGender())
            .patientRelation(patientRequest.patientRelation())
            .birthDate(patientBirthday)
            .nokPhone(getParsedNumber(patientRequest.nokPhone()))
            .build();
        return patientRepository.save(newPatient);
    }

    private static String getParsedNumber(String phoneNumber) {
        String parsedPhoneNumber = phoneNumber.replace("-", "");
        log.info(parsedPhoneNumber);
        if (parsedPhoneNumber.length() != 11) {
            throw new IllegalArgumentException("유효하지 않은 전화번호 입니다.");
        }
        return parsedPhoneNumber;
    }
}
