package com.team25.backend.validator;

import com.team25.backend.annotation.ValidTransportation;
import com.team25.backend.enumdomain.Transportation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class TransportationValidator implements ConstraintValidator<ValidTransportation, Transportation> {

    private static final List<Transportation> TRANSPORTATION = List.of(Transportation.TAXI,Transportation.WALK);

    @Override
    public boolean isValid(Transportation value, ConstraintValidatorContext context) {
        if (value==null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("운송 수단은 필수 입력값입니다.")
                .addConstraintViolation();
            return false;
        }

        if (!TRANSPORTATION.contains(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "유효하지 않은 운송 수단입니다. "
                )
                .addConstraintViolation();
            return false;
        }

        return true;
    }
}
