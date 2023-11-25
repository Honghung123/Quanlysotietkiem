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
@Document(collection = "tbl_term")
public class Term {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    @Field(name = "type")
    private int type;
    @Field(name = "name")
    private String name;
    @Field(name = "monthsOfTerm")
    private int monthsOfTerm;
    @Field(name = "interestRate")
    private Double interestRate;
    @Field(name = "minDeposit")
    private BigInteger minDeposit;
    @Field("minAdditionalDeposit")
    private BigInteger minAdditionalDeposit;
    @Field(name = "daysWithdrawn")
    private int daysWithdrawn;
}
