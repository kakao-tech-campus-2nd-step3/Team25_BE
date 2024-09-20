package com.team25.backend.validator;

import com.team25.backend.annotation.ValidArrivalLocation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ArrivalLocationValidator implements ConstraintValidator<ValidArrivalLocation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("출발지는 필수 입력값입니다.")
                .addConstraintViolation();
            return false;
        }
        return true;
    }
}
