package com.earntogether.starter_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tbl_phieugoitien")
public class PhieuGoiTien {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private int maphieu;
    private int makh;
    private int maso;
    private LocalDateTime date;
    private BigInteger money;
}
