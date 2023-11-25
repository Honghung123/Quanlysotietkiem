package com.earntogether.qlysotietkiem.entity;

import lombok.AllArgsConstructor;
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
@Document(collection = "tbl_passbook")
public class Passbook {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    @Field(name = "passbookCode")
    private int passbookCode;
    @Field(name = "customerCode")
    private int customerCode;
    @Field(name = "status")
    private int status;
    @Field(name = "term")
    private Term term;
    @Field(name = "dateCreated")
    private LocalDate dateCreated;
    @Field(name = "money")
    private BigInteger money;
}
