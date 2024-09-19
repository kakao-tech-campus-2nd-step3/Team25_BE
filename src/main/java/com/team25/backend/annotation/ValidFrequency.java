package com.team25.backend.annotation;

import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidInteger
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface ValidFrequency {

    String message() default "유효하지 않은 횟수입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
