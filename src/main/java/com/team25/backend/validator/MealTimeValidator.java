package com.team25.backend.validator;

import com.team25.backend.annotation.ValidMealTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class MealTimeValidator implements ConstraintValidator<ValidMealTime, String> {

    private static final List<String> MEALTIME = List.of("식전", "식후");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("복용 시간은 필수 입력값입니다.")
                .addConstraintViolation();
            return false;
        }

        if (!MEALTIME.contains(value.trim())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "유효하지 않은 복용 시간입니다. 유효한 복용시간: " + String.join(", ", MEALTIME))
                .addConstraintViolation();
            return false;
        }

        return true;
    }
}
