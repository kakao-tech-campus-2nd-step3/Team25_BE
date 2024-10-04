package com.team25.backend.service;

import com.team25.backend.dto.request.PatientRequest;
import com.team25.backend.entity.Patient;
import com.team25.backend.enumdomain.PatientGender;
import com.team25.backend.repository.PatientRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // 환자 정보 생성
    public Patient addPatient(PatientRequest patientRequest) {
        Patient newPatient = Patient.builder().name(patientRequest.name())
            .phoneNumber(patientRequest.phoneNumber()).
            patientGender(PatientGender.valueOf(patientRequest.patientGender())).
            patientRelation(patientRequest.patientRelation()).
            birthDate(LocalDateTime.parse(patientRequest.birthDate()))
            .nokPhone(patientRequest.nokPhone()).build();
        return patientRepository.save(newPatient);
    }
}
