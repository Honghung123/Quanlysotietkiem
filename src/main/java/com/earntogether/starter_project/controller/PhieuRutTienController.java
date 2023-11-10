package com.earntogether.starter_project.controller;

import com.earntogether.starter_project.entity.PhieuRutTien;
import com.earntogether.starter_project.service.PhieuRutTienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("api/ruttien")
public class PhieuRutTienController {
    @Autowired
    private PhieuRutTienService rutTienService;

    @GetMapping
    public ResponseEntity<List<PhieuRutTien>> getAllPhieuRutTien(){
        return new ResponseEntity<>(rutTienService.getAllPhieuRutTien(),
                HttpStatus.OK);
    }
}
