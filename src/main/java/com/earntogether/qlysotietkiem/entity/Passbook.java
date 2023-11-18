package com.earntogether.qlysotietkiem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tbl_sotietkiem")
public class Passbook {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private int maso;
    private int status;
    private int type;
    private LocalDate dateCreated;
    private BigInteger money;
    private KyHan kyHan;
}
