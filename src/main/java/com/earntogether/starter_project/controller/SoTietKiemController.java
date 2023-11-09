package com.earntogether.starter_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sotk")
public class SoTietKiemController {
    @GetMapping
    public ResponseEntity<String> getAllSoTietKiem(){
        return new ResponseEntity<>("All so tiet kiem hehe", HttpStatus.OK);
    }
}
