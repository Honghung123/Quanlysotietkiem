package com.earntogether.starter_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tbl_customer")
public class Customer {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private int makh;
    private String name;
    private String address;
    private String cmnd;
}
