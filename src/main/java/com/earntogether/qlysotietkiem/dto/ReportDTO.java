package com.earntogether.qlysotietkiem.dto;

import jakarta.validation.constraints.NotNull;

import java.time.YearMonth;

public record ReportDTO(int type, @NotNull YearMonth monthYear) {
}
