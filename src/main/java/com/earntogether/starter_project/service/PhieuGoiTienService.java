package com.earntogether.starter_project.service;

import com.earntogether.starter_project.entity.PhieuGoiTien;
import com.earntogether.starter_project.repository.PhieuGoiTienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhieuGoiTienService {
    @Autowired
    private PhieuGoiTienRepository goiTienRepository;
    public List<PhieuGoiTien> getAllPhieuGoiTien(){
        return goiTienRepository.findAll();
    }
}
