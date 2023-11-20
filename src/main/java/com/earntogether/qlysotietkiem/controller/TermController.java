package com.earntogether.qlysotietkiem.controller;

import com.earntogether.qlysotietkiem.dto.TermInsertDTO;
import com.earntogether.qlysotietkiem.dto.TermUpdateDTO;
import com.earntogether.qlysotietkiem.entity.Term;
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
    public Term getTermByType(@PathVariable int type){
        return termService.getTermByType(type);
    }

    @PostMapping
    public String insertTerm(@Valid TermInsertDTO termInsertDto){
        termService.insertTerm(termInsertDto);
        return "{\"message\": \"Thêm kỳ hạn thành công\"}";
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public String updateTerm(@Valid TermUpdateDTO termUpdateDto){
        termService.updateTerm(termUpdateDto);
        return "{\"message\": \"Cập nhật thành công\"}";
    }

    @DeleteMapping("/{type}")
    public String deleteTerm(@PathVariable int type){
        termService.deleteByType(type);
        return "{\"message\": \"Xoá kỳ hạn thành công\"}";
    }
}
