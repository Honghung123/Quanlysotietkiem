package com.earntogether.qlysotietkiem.dto;

import jakarta.validation.constraints.*;

import java.math.BigInteger;
import java.time.LocalDate;

public record CustomerPassbookDTO(
        @Size(min = 5, max = 5, message = "Mã số phải có đúng 5 số") String passbookCode,
        @NotEmpty(message = "Tên khách hàng trống") String name,
        @NotEmpty(message = "Địa chỉ không trống") String address,
        @NotNull(message = "Số tiền không hợp lệ") BigInteger money,
        @PositiveOrZero(message = "Mã sổ không hợp lệ") int type,
        @NotEmpty(message = "Chứng minh nhân dân không hợp lệ")
        @Size(min = 10, max = 10, message = "Chứng minh nhân dân phải đúng " +
                "10 số") String identityNumber,
        @NotNull(message = "Ngày mở sổ không hợp lệ") LocalDate dateOpened
        ) {}
