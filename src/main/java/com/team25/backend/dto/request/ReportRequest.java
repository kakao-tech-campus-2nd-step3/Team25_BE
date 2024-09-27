package com.team25.backend.dto.request;

import com.team25.backend.annotation.ValidFrequency;
import com.team25.backend.annotation.ValidMealTime;
import com.team25.backend.enumdomain.MealTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record ReportRequest(
    @NotBlank(message = "유효하지 않은 의사 소견입니다.") String doctorSummary,
    @ValidFrequency int frequency,
    @ValidMealTime MealTime mealTime,
    @NotBlank(message = "유효하지 않은 복용 횟수입니다") String timeOfDays) {
}
