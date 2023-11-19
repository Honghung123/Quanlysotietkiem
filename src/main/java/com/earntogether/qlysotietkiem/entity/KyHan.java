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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "tbl_kyhan")
public class KyHan {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    @Field(name = "type")
    private int type;
    @Field(name = "name")
    private String name;
    @Field(name = "numOfMonths")
    private int numOfMonths;
    @Field(name = "interestRate")
    private Double interestRate;
    @Field("dcguithem")
    private BigInteger duocGuiThem;
    @Field(name = "ngaydcrut")
    private int ngayDcRut;
    @Field(name = "minDeposit")
    private BigInteger minDeposit;
}
