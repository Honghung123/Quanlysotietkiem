package com.earntogether.qlysotietkiem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "tbl_customer_passbook")
public class Customer {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    @Field(name = "customerCode")
    private int customerCode;
    @Field(name = "name")
    private String name;
    @Field(name = "address")
    private String address;
    @Field(name = "identityNumber")
    private String identityNumber;
    @Field(name = "passbookCode")
    private int passbookCode;
}
