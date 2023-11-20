package com.earntogether.qlysotietkiem.dto;

import jakarta.validation.constraints.*;

import java.math.BigInteger;
import java.time.LocalDate;

public record CustomerPassbookDTO(
        @Positive(message = "Mã số không hợp lệ") int customerCode,
        @NotEmpty(message = "Tên khách hàng trống") String name,
        @NotEmpty(message = "Địa chỉ không trống") String address,
        @NotNull(message = "Số tiền không hợp lệ") BigInteger money,
        @PositiveOrZero int type,
        @NotEmpty(message = "Chứng minh nhân dân không hợp lệ") String identityNumber,
        @NotNull(message = "Ngày mở sổ không hợp lệ") LocalDate dateOpened
        ) {}
