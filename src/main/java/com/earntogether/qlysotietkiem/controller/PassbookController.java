package com.earntogether.qlysotietkiem.controller;

import com.earntogether.qlysotietkiem.dto.ReportDTO;
import com.earntogether.qlysotietkiem.entity.Passbook;
import com.earntogether.qlysotietkiem.model.AccountingModel;
import com.earntogether.qlysotietkiem.model.PassbookModel;
import com.earntogether.qlysotietkiem.model.ReportModel;
import com.earntogether.qlysotietkiem.service.PassbookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/passbook")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class PassbookController {
    private final PassbookService passbookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Passbook> getAllFullPassbookDetails(){
        return passbookService.getAllPassbook();
    }

    @GetMapping("/tracuu")
    @ResponseStatus(HttpStatus.OK)
    public List<PassbookModel> lookupPassbooks(){
        return passbookService.lookupPassbooks();
    }

    @GetMapping("/lookup")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> lookupPassbooks(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "per_page", defaultValue = "2") int per_page,
            @RequestParam(name = "sortBy", defaultValue = "makh") String sortBy
    ){
        return passbookService.lookupPassbooks(page, per_page, sortBy);
    }

    @GetMapping("/report")
    @ResponseStatus(HttpStatus.OK)
    public List<ReportModel> getOpenClosePassbookMonthlyReport(
            @Valid ReportDTO reportDto){
        return passbookService.getOpenClosePassbookMonthlyReport(reportDto);
    }

    @GetMapping("/daily-turnover")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountingModel> getDailyTurnover(@NotNull LocalDate date) {
        return passbookService.getDailyTurnover(date);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll(){
        passbookService.deleteAllPassbook();
    }
}
