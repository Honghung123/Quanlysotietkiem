package com.earntogether.starter_project.repository;

import com.earntogether.starter_project.entity.KyHan;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KyHanRepository extends MongoRepository<KyHan, String> {

}
