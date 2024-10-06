package com.team25.backend.validator;

import com.team25.backend.annotation.ValidServiceType;
import com.team25.backend.enumdomain.ServiceType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class ServiceTypeValidator implements ConstraintValidator<ValidServiceType, ServiceType> {

    private static final List<ServiceType> SERVICE_TYPE = List.of(
        ServiceType.GUARDIAN_PROXY,
        ServiceType.ADMISSION_DISCHARGE_SUPPORT,
        ServiceType.SPECIALIZED_ACCOMPANIMENT,
        ServiceType.REGULAR_ACCOMPANIMENT,
        ServiceType.CLINIC_ESCORT);

    @Override
    public boolean isValid(ServiceType value, ConstraintValidatorContext context) {
        if (value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("서비스 타입은 필수 입력값입니다.")
                .addConstraintViolation();
            return false;
        }

        if (!SERVICE_TYPE.contains(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "유효하지 않은 서비스 타입입니다.")
                .addConstraintViolation();
            return false;
        }

        return true;
    }
}
