package com.earntogether.qlysotietkiem.controller;

import com.earntogether.qlysotietkiem.dto.RutTienDTO;
import com.earntogether.qlysotietkiem.entity.PhieuRutTien;
import com.earntogether.qlysotietkiem.model.RutTienModel;
import com.earntogether.qlysotietkiem.service.PhieuRutTienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/ruttien")
@CrossOrigin(origins = "*")
public class PhieuRutTienController {
    @Autowired
    private PhieuRutTienService rutTienService;

    @GetMapping
    public ResponseEntity<List<RutTienModel>> getAllPhieuRutTien(){
        return ResponseEntity.ok(rutTienService.getAllPhieuRutTien());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String insert(@Valid RutTienDTO rutTienDto){
        String message = rutTienService.insert(rutTienDto);
        return String.format("{\"message\": \"%s\"}", message);
    }
}
