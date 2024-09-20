package com.team25.backend.annotation;

import com.team25.backend.validator.DepartureLocationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = DepartureLocationValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDepartureLocation {

    String message() default "유효하지 않은 출발지입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
