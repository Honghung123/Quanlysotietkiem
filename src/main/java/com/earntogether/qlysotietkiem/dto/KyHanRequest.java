package com.earntogether.qlysotietkiem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigInteger;

public record KyHanRequest(
        @PositiveOrZero(message = "Loại sổ không hợp lệ") int type,
        @NotEmpty(message = "Tên kỳ hạn không được để trống") String name,
        @PositiveOrZero(message = "Lãi suất không hợp lệ") Double interestRate,
        @Positive(message = "Số tiền tối thiểu không hợp lệ") BigInteger minDeposit,
        @Positive(message = "Số ngày rút không hợp lệ") int numOfDate
) {}
