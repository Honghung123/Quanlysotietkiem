package com.earntogether.qlysotietkiem.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigInteger;

@Builder
public record KyHanDTO(
        @NotEmpty(message = "Tên kỳ hạn không được trống") String name,
        @Positive(message = "Số tháng không hợp lệ") int numOfMonths,
        @PositiveOrZero(message = "Lãi suất không hợp lệ") double interestRate,
        @Positive(message = "Ngày gởi tối thiểu không hợp lệ") int minimumDay,
        @NotNull(message = "Số tiền gởi tối thiểu không hợp lệ") BigInteger minDeposit
) {}
