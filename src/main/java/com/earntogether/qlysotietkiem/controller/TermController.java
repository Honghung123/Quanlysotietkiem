package com.earntogether.qlysotietkiem.controller;

import com.earntogether.qlysotietkiem.dto.TermInsertDTO;
import com.earntogether.qlysotietkiem.dto.TermUpdateDTO;
import com.earntogether.qlysotietkiem.entity.Term;
import com.earntogether.qlysotietkiem.model.AppResponse;
import com.earntogether.qlysotietkiem.service.TermService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/term")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class TermController {
    private final TermService termService;

    @GetMapping
    public ResponseEntity<List<Term>> getAllTerm(){
        return new ResponseEntity<>(termService.getAllTerm(), HttpStatus.OK);
    }

    @GetMapping("/{type}")
    @ResponseStatus(HttpStatus.OK)
    public Term getTermByType(@PathVariable int type){
        return termService.getTermByType(type);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AppResponse insertTerm(@Valid TermInsertDTO termInsertDto){
        termService.insertTerm(termInsertDto);
        return new AppResponse(HttpStatus.OK.value(), "Thêm kỳ hạn thành công");
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AppResponse updateTerm(@Valid TermUpdateDTO termUpdateDto){
        termService.updateTerm(termUpdateDto);
        return new AppResponse(HttpStatus.OK.value(), "Cập nhật thành công");
    }

    @DeleteMapping("/{type}")
    @ResponseStatus(HttpStatus.OK)
    public AppResponse deleteTerm(@PathVariable int type){
        termService.deleteTermByType(type);
        return new AppResponse(HttpStatus.OK.value(), "Xoá kỳ hạn thành công");
    }
}
