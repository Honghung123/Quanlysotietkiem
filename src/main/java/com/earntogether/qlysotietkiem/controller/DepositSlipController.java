package com.earntogether.qlysotietkiem.controller;

import com.earntogether.qlysotietkiem.dto.DepositSlipDTO;
import com.earntogether.qlysotietkiem.model.DepositSlipModel;
import com.earntogether.qlysotietkiem.model.AppResponse;
import com.earntogether.qlysotietkiem.service.DepositSlipService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/deposit")
@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class DepositSlipController {
    private final DepositSlipService depositService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DepositSlipModel> getAllDepositSlip(){
        return depositService.getAllDepositSlip();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AppResponse insertDepositSlip(@Valid DepositSlipDTO depositSlipDto){
        depositService.insertDepositSlip(depositSlipDto);
        return new AppResponse(HttpStatus.OK.value(),"Gởi tiền thành công!");
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllDepositSlip(){
        depositService.deleteAllDepositSlip();
    }
}
