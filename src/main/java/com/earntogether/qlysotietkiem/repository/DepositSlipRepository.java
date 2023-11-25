package com.earntogether.qlysotietkiem.repository;

import com.earntogether.qlysotietkiem.entity.DepositSlip;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DepositSlipRepository extends MongoRepository<DepositSlip,
        String> {
    @Query(value = "{'type':?0, 'depositDate': ?1}")
    List<DepositSlip> findByTypeAndDepositDate(int type, LocalDate depositDate);

    void deleteAll();
}
