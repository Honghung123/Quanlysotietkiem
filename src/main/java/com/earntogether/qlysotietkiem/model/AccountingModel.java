package com.earntogether.qlysotietkiem.model;


import java.math.BigInteger;

public record AccountingModel(String tenKyHan, int type, BigInteger tongThu,
                              BigInteger tongChi) {
}
