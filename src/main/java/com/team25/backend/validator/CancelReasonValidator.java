package com.team25.backend.validator;

import static com.team25.backend.enumdomain.CancelReason.CAREGIVER_CANCEL;
import static com.team25.backend.enumdomain.CancelReason.CHANGE_OF_MIND;
import static com.team25.backend.enumdomain.CancelReason.MANAGER_CANCEL;
import static com.team25.backend.enumdomain.CancelReason.NO_CANCEL;
import static com.team25.backend.enumdomain.CancelReason.NO_NANGER_MATCHED;
import static com.team25.backend.enumdomain.CancelReason.PATIENT_CANCEL;
import static com.team25.backend.enumdomain.CancelReason.PAYMENT_FAILER;
import static com.team25.backend.enumdomain.CancelReason.SECHEDULED_REBOOK;

import com.team25.backend.annotation.ValidCancelReason;
import com.team25.backend.enumdomain.CancelReason;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class CancelReasonValidator implements ConstraintValidator<ValidCancelReason, CancelReason> {

    private static final List<CancelReason> VALID_REASONS = Arrays.asList(
        PATIENT_CANCEL,
        MANAGER_CANCEL,
        CAREGIVER_CANCEL,
        CHANGE_OF_MIND,
        SECHEDULED_REBOOK,
        NO_CANCEL,
        NO_NANGER_MATCHED,
        PAYMENT_FAILER
    );

    @Override
    public boolean isValid(CancelReason value, ConstraintValidatorContext context) {
        if (value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("취소 이유는 필수 입력값입니다.")
                .addConstraintViolation();
            return false;
        }

        if (!VALID_REASONS.contains(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "유효하지 않은 취소 이유입니다. 유효한 이유: " + String.join(", ", (CharSequence) VALID_REASONS))
                .addConstraintViolation();
            return false;
        }

        return true;
    }

}
