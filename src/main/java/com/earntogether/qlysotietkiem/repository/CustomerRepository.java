package com.earntogether.qlysotietkiem.repository;

import com.earntogether.qlysotietkiem.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer,
        String> {
    Optional<Customer> findByCustomerCode(int customerCode);
    Optional<Customer> findByIdentityNumber(String identityNumber);
    Optional<Customer> deleteByCustomerCode(int customerCode);
    long count();
    Optional<Customer> findByNameAndPassbookCode(String name, int passbookCode);
}
