package com.team25.backend.domain.report.dto.response;

import org.springframework.validation.annotation.Validated;

@Validated
public record ReportResponse(
    String doctorSummary,
    int frequency,
    String medicineTime,
    String timeOfDays) {
}
