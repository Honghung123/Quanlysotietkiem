package com.earntogether.starter_project.repository;

import com.earntogether.starter_project.entity.PhieuRutTien;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuRutTienRepository extends MongoRepository<PhieuRutTien,
        String> {

}
