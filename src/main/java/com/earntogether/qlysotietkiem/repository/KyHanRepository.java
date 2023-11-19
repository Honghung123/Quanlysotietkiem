package com.earntogether.qlysotietkiem.repository;

import com.earntogether.qlysotietkiem.entity.KyHan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KyHanRepository extends MongoRepository<KyHan, String> {
    Optional<KyHan> findByType(int type);

    Optional<KyHan> findByNumOfMonths(int numOfMonths);

    Optional<KyHan> findByName(String name);

    void deleteByType(int type);
}
