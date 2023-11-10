package com.earntogether.starter_project.controller;

import com.earntogether.starter_project.entity.PhieuGoiTien;
import com.earntogether.starter_project.service.PhieuGoiTienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("api/goitien")
@RestController
public class PhieuGoiTienController {
    @Autowired
    private PhieuGoiTienService goiTienService;

    @GetMapping
    public ResponseEntity<List<PhieuGoiTien>> getAllPhieuGoiTien(){
        return new ResponseEntity<>(goiTienService.getAllPhieuGoiTien(),
                HttpStatus.OK);
    }
}
