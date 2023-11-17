package com.earntogether.qlysotietkiem.controller;

import com.earntogether.qlysotietkiem.dto.KyHanDTO;
import com.earntogether.qlysotietkiem.entity.KyHan;
import com.earntogether.qlysotietkiem.service.KyHanService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/kyhan")
@CrossOrigin(origins = "*")
public class KyHanController {
    @Autowired
    private KyHanService kyHanService;

    @GetMapping
    public ResponseEntity<List<KyHan>> getAllKyHan(){
        return new ResponseEntity<>(kyHanService.getAllKyHan(), HttpStatus.OK);
    }

    @PostMapping
    public String addNewKyHan(@NotNull KyHanDTO kyHanDto){
        kyHanService.addNewKyHan(kyHanDto);
        return "{\"message\": \"Them thanh cong\"}";
    }
}
