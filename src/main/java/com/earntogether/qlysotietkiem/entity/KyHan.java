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
public class    KyHan {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private int type;
    private String name;
    private int month;
    private Double laisuat;
    @Field("dcguithem")
    private BigInteger duocGuiThem;
    @Field(name = "ngaydcrut")
    private int ngayDcRut;
    @Field(name = "min_deposit")
    private BigInteger minDeposit;
}
