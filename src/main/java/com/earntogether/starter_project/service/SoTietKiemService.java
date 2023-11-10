package com.earntogether.starter_project.service;

import com.earntogether.starter_project.entity.SoTietKiem;
import com.earntogether.starter_project.repository.SoTietKiemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoTietKiemService {
    @Autowired
    private SoTietKiemRepository sotkRepository;
    public List<SoTietKiem> getAllSotk(){
        return sotkRepository.findAll();
    }
}
