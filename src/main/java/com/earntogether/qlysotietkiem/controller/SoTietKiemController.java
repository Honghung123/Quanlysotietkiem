package com.earntogether.qlysotietkiem.controller;

import com.earntogether.qlysotietkiem.dto.ReportDTO;
import com.earntogether.qlysotietkiem.entity.SoTietKiem;
import com.earntogether.qlysotietkiem.model.KeToanNgay;
import com.earntogether.qlysotietkiem.model.ReportModel;
import com.earntogether.qlysotietkiem.model.SoTietKiemModel;
import com.earntogether.qlysotietkiem.service.SoTietKiemService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class SoTietKiemController {
    @Autowired
    private SoTietKiemService soTkService;

    @GetMapping("/sotk")
    public List<SoTietKiem> getAll(){
        return soTkService.getAll();
    }

    @GetMapping("/tracuu")
    @ResponseStatus(HttpStatus.OK)
    public List<SoTietKiemModel> getAllSoTietKiem(){
        return soTkService.searchAll();
    }

    @GetMapping("/tracuus")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getAllSoTietKiem(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "per_page", defaultValue = "2") int per_page,
            @RequestParam(name = "sortBy", defaultValue = "makh") String sortBy
    ){
        return soTkService.tracuu(page, per_page, sortBy);
    }

    @PostMapping("/doanhthuDaily")
    @ResponseStatus(HttpStatus.OK)
    public List<KeToanNgay> getAvernueByDate(@NotNull LocalDate date){
        return soTkService.getAvernueByDate(date);
    }

    @GetMapping("/report")
    public List<ReportModel> getReportByMonth(
            @RequestParam(name = "type") int type,
            @RequestParam(name = "monthYear") String monthYear
    ){
        return soTkService.getReportByMonth(type, monthYear);
    }
}
