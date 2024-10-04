package com.team25.backend.dto.request;

import com.team25.backend.enumdomain.PatientGender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

public record PatientRequest(
    @NotNull String name,
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}",message = "잘못된 전화번호 형식입니다. 'XXX-XXXX-XXXX' 형식으로 입력해주세요.")
    @NotNull String phoneNumber,
    @NotNull PatientGender patientGender,
    @NotNull String patientRelation,
    @NotNull LocalDateTime birthDate,
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}",message = "잘못된 전화번호 형식입니다. 'XXX-XXXX-XXXX' 형식으로 입력해주세요.")
    @NotNull String nokPhone
) {

}
