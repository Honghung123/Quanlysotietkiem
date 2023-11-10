package com.earntogether.starter_project.service;

import com.earntogether.starter_project.entity.KyHan;
import com.earntogether.starter_project.repository.KyHanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KyHanService {
    @Autowired
    private KyHanRepository kyHanRepository;
    public List<KyHan> getAllKyHan(){
        return kyHanRepository.findAll();
    }
}
