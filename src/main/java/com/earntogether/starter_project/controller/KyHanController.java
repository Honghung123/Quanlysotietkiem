package com.earntogether.starter_project.controller;

import com.earntogether.starter_project.entity.KyHan;
import com.earntogether.starter_project.service.KyHanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/kyhan")
public class KyHanController {
    @Autowired
    private KyHanService kyHanService;

    @GetMapping
    public ResponseEntity<List<KyHan>> getAllKyHan(){
        return new ResponseEntity<>(kyHanService.getAllKyHan(), HttpStatus.OK);
    }
}
