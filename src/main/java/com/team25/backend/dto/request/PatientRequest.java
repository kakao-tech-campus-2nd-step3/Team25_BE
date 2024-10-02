package com.team25.backend.dto.request;

public record PatientRequest(
    String name,
    String phoneNumber,
    String patientGender,
    String patientRelation,
    String birthDate,
    String nokPhone
) {

}
