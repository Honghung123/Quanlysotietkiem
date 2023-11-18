package com.earntogether.qlysotietkiem.controller;

import com.earntogether.qlysotietkiem.dto.ReportDTO;
import com.earntogether.qlysotietkiem.entity.Passbook;
import com.earntogether.qlysotietkiem.model.AccountingModel;
import com.earntogether.qlysotietkiem.model.ReportModel;
import com.earntogether.qlysotietkiem.service.PassbookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/passbook")
@AllArgsConstructor
public class PassbookController {
    private final PassbookService passbookService;

    @GetMapping
    public List<Passbook> getAllFullPassbookDetails(){
        return passbookService.getAll();
    }

    @GetMapping("/report")
    public List<ReportModel> getOpenClosePassbookReportByMonth(
            @Valid ReportDTO reportDto){
        return passbookService.getOpenClosePassbookReportByMonth(reportDto);
    }

    @GetMapping("/daily-turnover")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountingModel> getDailyTurnover(@NotNull LocalDate date){
        return passbookService.getDailyTurnover(date);
    }
}
