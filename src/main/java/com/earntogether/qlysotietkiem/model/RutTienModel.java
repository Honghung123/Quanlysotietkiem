package com.earntogether.qlysotietkiem.model;

import lombok.Builder;

import java.math.BigInteger;
import java.time.LocalDate;

@Builder
public record RutTienModel(String id, int makh, String name, int maso,
                           int type, LocalDate date, BigInteger money) {
}
