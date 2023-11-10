package com.earntogether.starter_project.service;

import com.earntogether.starter_project.entity.PhieuRutTien;
import com.earntogether.starter_project.repository.PhieuRutTienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhieuRutTienService {
    @Autowired
    private PhieuRutTienRepository phieuRutTienRepository;
    public List<PhieuRutTien> getAllPhieuRutTien(){
        return phieuRutTienRepository.findAll();
    }

}
