package com.earntogether.qlysotietkiem.controller;

import com.earntogether.qlysotietkiem.dto.DepositSlipDTO;
import com.earntogether.qlysotietkiem.model.WithdrawalSlipModel;
import com.earntogether.qlysotietkiem.service.DepositSlipService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/deposit")
@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class DepositSlipController {
    private final DepositSlipService goiTienService;

    @GetMapping
    public ResponseEntity<List<WithdrawalSlipModel>> getAllPhieuGoiTien(){
        return ResponseEntity.ok(goiTienService.getAllPhieuGoiTien());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String insert(@Valid DepositSlipDTO depositSlipDto){
        goiTienService.insert(depositSlipDto);
        return "{\"message\" : \"Gởi tiền thành công!\"}";
    }
}
