package com.earntogether.qlysotietkiem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;

public record WithdrawalSlipDTO(
    @Positive(message = "Mã sổ không hợp lệ") int passbookCode,
    @NotEmpty(message = "Thiếu tên khách hàng") String customerName,
    @NotNull(message = "Ngày rút tiền không hợp lệ") LocalDate withdrawalDate,
    @NotNull(message = "Số tiền rút không hợp lệ") BigInteger money
){}
