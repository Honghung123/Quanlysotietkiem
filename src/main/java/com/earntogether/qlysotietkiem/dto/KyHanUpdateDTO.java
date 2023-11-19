package com.earntogether.qlysotietkiem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.math.BigInteger;

@Builder
public record KyHanUpdateDTO(
        @PositiveOrZero(message = "Loại kỳ hạn không hợp lệ") int type,
        @NotNull(message = "Số tiền gửi tối thiểu không hợp lệ") BigInteger minDeposit,
        @Positive(message = "Số ngày gửi tối thiểu không hợp lệ") int minimumDay,
        @PositiveOrZero(message = "Lãi suất không hợp lệ") double interestRate) {
}
