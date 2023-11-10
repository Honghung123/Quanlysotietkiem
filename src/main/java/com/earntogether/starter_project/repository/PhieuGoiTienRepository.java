package com.earntogether.starter_project.repository;

import com.earntogether.starter_project.entity.PhieuGoiTien;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuGoiTienRepository extends MongoRepository<PhieuGoiTien,
        String> {
}
