package com.team25.backend.validator;

import com.team25.backend.annotation.ValidInteger;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IntegerValidator implements ConstraintValidator<ValidInteger, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        try {
            int price = value;
            if (price < 0) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("입력값은 0 이상이어야 합니다.")
                    .addConstraintViolation();
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("입력값은 정수여야 합니다.")
                .addConstraintViolation();
            return false;
        }
    }
}
