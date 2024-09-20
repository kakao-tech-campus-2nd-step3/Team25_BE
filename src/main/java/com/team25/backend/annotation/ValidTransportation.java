package com.team25.backend.annotation;

import com.team25.backend.validator.TransportationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TransportationValidator.class)
public @interface ValidTransportation {

    String message() default "유효하지 않은 운송 수단입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
