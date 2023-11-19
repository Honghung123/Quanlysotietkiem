package com.earntogether.qlysotietkiem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.YearMonth;

public record ReportDTO(
        @PositiveOrZero(message = "Loại kỳ hạn không hợp lệ") int type,
        @NotNull(message = "Năm tháng không hợp lệ") YearMonth monthYear
) {}
