package com.earntogether.qlysotietkiem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "tbl_withdrawalSlip")
public class WithdrawalSlip {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    @Field(name = "customerCode")
    private int customerCode;
    @Field(name = "passbookCode")
    private int passbookCode;
    @Field(name = "type")
    private int type;
    @Field(name = "withdrawalDate")
    private LocalDate withdrawalDate;
    @Field(name = "money")
    private BigInteger money;
}
