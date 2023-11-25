package com.earntogether.qlysotietkiem.repository;

import com.earntogether.qlysotietkiem.entity.WithdrawalSlip;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WithdrawalSlipRepository extends MongoRepository<WithdrawalSlip,
        String> {
    @Query("{'type' : ?0, 'withdrawalDate': ?1}")
    List<WithdrawalSlip> findByTypeAndWithdrawalDate(int type, LocalDate withdrawalDate);

    void deleteAll();
}
