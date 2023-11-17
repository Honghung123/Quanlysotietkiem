package com.earntogether.qlysotietkiem.repository;

import com.earntogether.qlysotietkiem.entity.SoTietKiem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SoTietKiemRepository extends MongoRepository<SoTietKiem,
        String> {

    Optional<SoTietKiem> findByMaso(int maso);

    List<SoTietKiem> findByTypeAndDateCreated(int type, LocalDate date);
}
