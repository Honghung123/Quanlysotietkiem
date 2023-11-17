package com.earntogether.qlysotietkiem.controller;

import com.earntogether.qlysotietkiem.dto.GoiTienDTO;
import com.earntogether.qlysotietkiem.entity.PhieuGoiTien;
import com.earntogether.qlysotietkiem.model.GoiTienModel;
import com.earntogether.qlysotietkiem.service.PhieuGoiTienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/goitien")
@RestController
@CrossOrigin(origins = "*")
public class PhieuGoiTienController {
    @Autowired
    private PhieuGoiTienService goiTienService;

    @GetMapping
    public ResponseEntity<List<GoiTienModel>> getAllPhieuGoiTien(){
        return ResponseEntity.ok(goiTienService.getAllPhieuGoiTien());
    }
    @GetMapping("/date")
    @ResponseStatus(HttpStatus.OK)
    public List<PhieuGoiTien> getPhieuGoiTienByDate(){
        return goiTienService.getPhieuGoiTienByDate();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String insert(@Valid GoiTienDTO goiTienDto){
        goiTienService.insert(goiTienDto);
        return "{\"message\" : \"Gởi tiền thành công!\"}";
    }
}
