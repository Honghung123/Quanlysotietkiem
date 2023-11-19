package com.earntogether.qlysotietkiem.controller;

import com.earntogether.qlysotietkiem.dto.KyHanDTO;
import com.earntogether.qlysotietkiem.dto.KyHanUpdateDTO;
import com.earntogether.qlysotietkiem.entity.KyHan;
import com.earntogether.qlysotietkiem.service.KyHanService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/kyhan")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class KyHanController {
    private final KyHanService kyHanService;

    @GetMapping
    public ResponseEntity<List<KyHan>> getAllKyHan(){
        return new ResponseEntity<>(kyHanService.getAllKyHan(), HttpStatus.OK);
    }

    @GetMapping("/{type}")
    public KyHan getKyHanByType(@PathVariable int type){
        return kyHanService.getKyHanByType(type);
    }

    @PostMapping
    public String insertKyHan(@Valid KyHanDTO kyHanDto){
        kyHanService.insertKyHan(kyHanDto);
        return "{\"message\": \"Them thanh cong\"}";
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public String updateKyHan(@Valid KyHanUpdateDTO kyHanUpdateDto){
        kyHanService.updateKyHan(kyHanUpdateDto);
        return "{\"message\": \"Cap nhat thanh cong\"}";
    }

    @DeleteMapping("/{type}")
    public String deleteKyHan(@PathVariable int type){
        kyHanService.deleteByType(type);
        return "{\"message\": \"Xoa thanh cong\"}";
    }
}
