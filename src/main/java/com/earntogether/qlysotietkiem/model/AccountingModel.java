package com.earntogether.qlysotietkiem.model;


import java.math.BigInteger;

public record AccountingModel(String termName, int type,
                              BigInteger totalRevenue,
                              BigInteger totalExpenditure
) {}
