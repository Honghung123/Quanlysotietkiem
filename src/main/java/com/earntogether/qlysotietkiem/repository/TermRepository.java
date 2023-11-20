package com.earntogether.qlysotietkiem.repository;

import com.earntogether.qlysotietkiem.entity.Term;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TermRepository extends MongoRepository<Term, String> {
    Optional<Term> findByType(int type);

    Optional<Term> findByMonthsOfTerm(int monthsOfTerm);

    Optional<Term> deleteByType(int type);
}
