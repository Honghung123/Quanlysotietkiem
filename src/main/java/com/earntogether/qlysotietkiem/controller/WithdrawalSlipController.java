package com.earntogether.qlysotietkiem.controller;

import com.earntogether.qlysotietkiem.dto.WithdrawalSlipDTO;
import com.earntogether.qlysotietkiem.model.DepositSlipModel;
import com.earntogether.qlysotietkiem.service.WithdrawalSlipService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/takeout")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class WithdrawalSlipController {
    private final WithdrawalSlipService rutTienService;

    @GetMapping
    public ResponseEntity<List<DepositSlipModel>> getAllPhieuRutTien(){
        return ResponseEntity.ok(rutTienService.getAllPhieuRutTien());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String insert(@Valid WithdrawalSlipDTO withdrawalSlipDto){
        String message = rutTienService.insert(withdrawalSlipDto);
        return String.format("{\"message\": \"%s\"}", message);
    }
}
