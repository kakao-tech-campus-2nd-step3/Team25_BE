package com.team25.backend.validator;

import com.team25.backend.annotation.ValidPrice;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriceValidator implements ConstraintValidator<ValidPrice, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // IntegerValidator의 검증을 먼저 수행
        IntegerValidator integerValidator = new IntegerValidator();
        if (!integerValidator.isValid(value, context)) {
            return false;
        }
        // 추가적인 가격 관련 검증
        return value >= 0; // 예: 가격은 0 이상이어야 함
    }
}