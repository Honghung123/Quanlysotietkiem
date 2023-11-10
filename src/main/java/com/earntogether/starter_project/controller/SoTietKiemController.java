package com.earntogether.starter_project.controller;

import com.earntogether.starter_project.entity.SoTietKiem;
import com.earntogether.starter_project.service.SoTietKiemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/sotk")
public class SoTietKiemController {
    @Autowired
    private SoTietKiemService soTkService;
    @GetMapping
    public ResponseEntity<List<SoTietKiem>> getAllSoTietKiem(){
        return new ResponseEntity<>(soTkService.getAllSotk(),
                HttpStatus.OK);
    }
}
