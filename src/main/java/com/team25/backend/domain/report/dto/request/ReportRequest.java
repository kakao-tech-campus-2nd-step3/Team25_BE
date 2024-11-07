package com.team25.backend.domain.report.dto.request;

import com.team25.backend.domain.report.enumdomain.MedicineTime;

public record ReportRequest(
    String doctorSummary,
    int frequency,
    MedicineTime medicineTime,
    String timeOfDays) {
}
