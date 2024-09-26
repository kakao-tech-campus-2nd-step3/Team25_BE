package com.team25.backend.dto.response;

import com.team25.backend.annotation.ValidFrequency;
import com.team25.backend.annotation.ValidMealTime;
import com.team25.backend.enumdomain.MealTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record ReportResponse(
    String doctorSummary,
    int frequency,
    MealTime mealTime,
    String timeOfDays) {
}
