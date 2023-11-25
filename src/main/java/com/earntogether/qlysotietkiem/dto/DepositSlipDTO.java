package com.earntogether.qlysotietkiem.dto;

import jakarta.validation.constraints.*;

import java.math.BigInteger;
import java.time.LocalDate;

public record DepositSlipDTO(
    @Positive(message = "Mã sổ không hợp lệ") int passbookCode,
    @NotEmpty(message = "Thiếu tên khách hàng") String customerName,
    @NotNull(message = "Ngày gửi tiền không hợp lệ") LocalDate depositDate,
    @Positive(message = "Số tiền gửi không hợp lệ") BigInteger money
){}
