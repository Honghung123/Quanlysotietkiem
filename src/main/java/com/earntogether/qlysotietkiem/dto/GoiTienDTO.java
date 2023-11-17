package com.earntogether.qlysotietkiem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoiTienDTO {
    @Min(value= 1, message = "Mã số khách hàng không hợp lệ")
    private int maso;
    @NotEmpty(message = "Thiếu tên khách hàng")
    private String name;
    @NotNull(message = "Ngày gửi tiền không hợp lệ")
    private LocalDate dateSent;
    @Min(value = 0, message = "Số tiền gửi không hợp lệ")
    private BigInteger money;
}
