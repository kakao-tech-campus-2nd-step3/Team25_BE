package com.team25.backend.service;

import com.team25.backend.dto.request.PatientRequest;
import com.team25.backend.entity.Patient;
import com.team25.backend.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // 환자 정보 생성
    public Patient addPatient(PatientRequest patientRequest) {
        Patient newPatient = Patient.builder()
            .name(patientRequest.name())
            .phoneNumber(getParsedNumber(patientRequest.phoneNumber()))
            .gender(patientRequest.patientGender())
            .patientRelation(patientRequest.patientRelation())
            .birthDate(patientRequest.birthDate())
            .nokPhone(getParsedNumber(patientRequest.nokPhone()))
            .build();
        return patientRepository.save(newPatient);
    }

    private static String getParsedNumber(String phoneNumber) {
        String parsedPhoneNumber = phoneNumber.replace("-", "");
        if (parsedPhoneNumber.length() != 11 || !phoneNumber.matches("\\d+")) {
            throw new IllegalArgumentException("유효하지 않은 전화번호 입니다.");
        }
        return parsedPhoneNumber;
    }
}
