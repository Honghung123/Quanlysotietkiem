package com.earntogether.qlysotietkiem.model;

import lombok.Builder;

import java.math.BigInteger;
import java.time.LocalDate;

@Builder
public record DepositSlipModel(String id, int makh, int maso,
                               int type, LocalDate date, BigInteger money) {
}
