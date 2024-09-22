package com.team25.backend.annotation;

import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidInteger {

    String message() default "유효하지 않은 정수입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
