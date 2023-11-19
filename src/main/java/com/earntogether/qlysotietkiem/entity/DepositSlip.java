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
@Document(collection = "tbl_depositSlip")
public class DepositSlip {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    @Field(name = "customerCode")
    private int customerCode;
    @Field(name = "passbookCode")
    private int passbookCode;
    @Field(name = "type")
    private int type;
    @Field(name = "depositDate")
    private LocalDate depositDate;
    @Field(name = "money")
    private BigInteger money;
}
