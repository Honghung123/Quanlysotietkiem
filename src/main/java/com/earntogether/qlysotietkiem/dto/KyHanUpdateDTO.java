package com.earntogether.qlysotietkiem.dto;

import lombok.Builder;

import java.math.BigInteger;

@Builder
public record KyHanUpdateDTO(int type, BigInteger min_deposit, int minDay,
                             double laisuat) {
}
