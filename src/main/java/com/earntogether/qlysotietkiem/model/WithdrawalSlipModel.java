package com.earntogether.qlysotietkiem.model;

import lombok.Builder;

import java.math.BigInteger;
import java.time.LocalDate;

@Builder
public record WithdrawalSlipModel(String id, int customerCode,
                                  int passbookCode,
                                  int type, LocalDate withdrawalDate,
                                  BigInteger money
) {}
