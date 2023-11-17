package com.earntogether.qlysotietkiem.repository;

import com.earntogether.qlysotietkiem.entity.PhieuRutTien;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PhieuRutTienRepository extends MongoRepository<PhieuRutTien,
        String> {
    @Query("{'type' : ?0, 'date': ?1}")
    List<PhieuRutTien> findByTypeAndDate(int type, LocalDate date);
}