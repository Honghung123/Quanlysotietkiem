package com.earntogether.starter_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "tbl_customer")
public class Customer {
    @Id
    private ObjectId id;
    private int makh;
    private String name;
    private String address;
    private String cmnd;
}
