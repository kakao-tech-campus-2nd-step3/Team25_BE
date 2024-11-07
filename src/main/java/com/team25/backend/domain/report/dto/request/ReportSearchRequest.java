package com.team25.backend.domain.report.dto.request;

import com.team25.backend.domain.report.enumdomain.MedicineTime;

public record ReportSearchRequest(
    MedicineTime medicineTime
    ) {
}
