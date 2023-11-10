package com.earntogether.starter_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tbl_kyhan")
public class KyHan {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private int type;
    private String name;
    private Double laisuat;
    @Field("dcguithem")
    private BigInteger duocGuiThem;
    @Field(name = "ngaydcrut")
    private int ngayDcRut;
    @Field(name = "min_deposit")
    private BigInteger minDeposit;
}
