package com.team25.backend.validator;

import com.team25.backend.annotation.ValidServiceType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class ServiceTypeValidator implements ConstraintValidator<ValidServiceType, String> {

    private static final List<String> SERVICE_TYPE = List.of("동행", "진료", "응급", "외래진료", "내시경검사");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("서비스 타입은 필수 입력값입니다.")
                .addConstraintViolation();
            return false;
        }

        if (!SERVICE_TYPE.contains(value.trim())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "유효하지 않은 서비스 타입입니다. 유효한 서비스 타입: " + String.join(", ", SERVICE_TYPE))
                .addConstraintViolation();
            return false;
        }

        return true;
    }
}
