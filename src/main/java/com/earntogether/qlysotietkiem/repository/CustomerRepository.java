package com.earntogether.qlysotietkiem.repository;

import com.earntogether.qlysotietkiem.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer,
        String> {
    Optional<Customer> findByMakh(int makh);
    void deleteByMakh(int makh);
    long count();

//    Optional<Customer> findBySotk_Maso(int maso);
    Optional<Customer> findByNameAndSotkMaso(String name, int maso);

    String findNameByMakh(int makh);
}
