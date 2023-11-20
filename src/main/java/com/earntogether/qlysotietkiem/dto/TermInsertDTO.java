package com.earntogether.qlysotietkiem.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigInteger;

@Builder
public record TermInsertDTO(
        @PositiveOrZero(message = "Số tháng kỳ hạn không hợp lệ") int monthsOfTerm,
        @PositiveOrZero(message = "Lãi suất không hợp lệ") double interestRate,
        @NotNull(message = "Số tiền gởi tối thiểu không hợp lệ") BigInteger minDeposit
) {}
