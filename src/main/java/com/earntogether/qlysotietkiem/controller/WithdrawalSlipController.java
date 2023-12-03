package com.earntogether.qlysotietkiem.controller;

import com.earntogether.qlysotietkiem.dto.WithdrawalSlipDTO;
import com.earntogether.qlysotietkiem.model.AppResponse;
import com.earntogether.qlysotietkiem.model.WithdrawalSlipModel;
import com.earntogether.qlysotietkiem.service.WithdrawalSlipService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/withdraw")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class WithdrawalSlipController {
    private final WithdrawalSlipService withdrawalService;

    @GetMapping
    public ResponseEntity<List<WithdrawalSlipModel>> getAllWithdrawalSlip(){
        return ResponseEntity.ok(withdrawalService.getAllWithdrawalSlip());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AppResponse insertWithdrawalSlip(@Valid WithdrawalSlipDTO withdrawalSlipDto){
        String message = withdrawalService.insertWithdrawalSlip(withdrawalSlipDto);
        return new AppResponse(HttpStatus.OK.value(), message);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll(){
        withdrawalService.deleteAllWithdrawalSlip();
    }
}
