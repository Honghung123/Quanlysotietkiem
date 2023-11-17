package com.earntogether.qlysotietkiem.dto;

import lombok.Builder;

import java.math.BigInteger;

@Builder
public record KyHanDTO(String name, int month, double laisuat, int ngayDcGui,
                       BigInteger min_deposit) {
}
