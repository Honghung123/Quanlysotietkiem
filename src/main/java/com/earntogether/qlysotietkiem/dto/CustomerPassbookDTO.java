package com.earntogether.qlysotietkiem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;
import java.time.LocalDate;

public record CustomerPassbookDTO(
        @NotNull int makh,
        @NotEmpty(message = "Tên khách hàng trống") String name,
        @NotEmpty(message = "Địa chỉ không trống") String address,
        @Min(value = 1,message = "Số tiền không hợp lệ") BigInteger money,
        @NotNull int type,
        @NotEmpty String cmnd,
        @NotNull LocalDate dateOpened
        ) {
}
