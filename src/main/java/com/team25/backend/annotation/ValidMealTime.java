package com.team25.backend.annotation;

import com.team25.backend.validator.MealTimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Constraint(validatedBy = MealTimeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMealTime {

    String message() default "유효하지 않은 식사 시간입니다";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
