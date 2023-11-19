package com.earntogether.qlysotietkiem.model;

import com.earntogether.qlysotietkiem.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

public record PassbookModel(int passbookCode, int type, String customerName,
                            BigInteger money
){}