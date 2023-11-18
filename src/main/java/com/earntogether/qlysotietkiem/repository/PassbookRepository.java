package com.earntogether.qlysotietkiem.repository;

import com.earntogether.qlysotietkiem.entity.Passbook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PassbookRepository extends MongoRepository<Passbook,
        String> {

    Optional<Passbook> findByMaso(int maso);

    List<Passbook> findByTypeAndDateCreated(int type, LocalDate date);
}
