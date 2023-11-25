package com.earntogether.qlysotietkiem.service;

import com.earntogether.qlysotietkiem.dto.TermInsertDTO;
import com.earntogether.qlysotietkiem.dto.TermUpdateDTO;
import com.earntogether.qlysotietkiem.entity.Term;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.repository.TermRepository;
import com.earntogether.qlysotietkiem.utils.converter.TermConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermService {
    @Autowired
    private TermRepository termRepository;
    public List<Term> getAllTerm(){
        return termRepository.findAll();
    }

    public void insertTerm(TermInsertDTO termInsertDto) {
        var termName = termInsertDto.monthsOfTerm() == 0 ?
                 "Không kỳ hạn" : termInsertDto.monthsOfTerm() + " tháng";
        if(termRepository.findByMonthsOfTerm(termInsertDto.monthsOfTerm()).isPresent()){
            throw new DataNotValidException("Đã tồn tại kỳ hạn " + termName);
        }
        int type = this.generateNewType();
        Term term = TermConverter.convertDTOtoEntity(termInsertDto, type);
        termRepository.save(term);
        System.out.println("-> Inserted " + term);
    }

    private int generateNewType() {
        int newType = 0;
        while(termRepository.findByType(newType).isPresent()){
            newType++;
        }
        return newType;
    }

    public void deleteTermByType(int type){
        termRepository.deleteByType(type).ifPresentOrElse(
                term -> System.out.println("-> Deleted kỳ hạn có type = " + type),
                () -> {throw new ResourceNotFoundException("Không thể " +
                        "xóa vì không tồn tại kỳ hạn có type = " + type);}
        );

    }

    public Term getTermByType(int type) {
        return termRepository.findByType(type).orElseThrow(
                () -> new ResourceNotFoundException("Không tồn tại kỳ " +
                        "hạn có type = " + type));
    }

    public void updateTerm(TermUpdateDTO termUpdateDto) {
        int type = termUpdateDto.type();
        var term = termRepository.findByType(type).orElseThrow(
                () -> new ResourceNotFoundException("Không tìm thấy kỳ " +
                        "hạn có type = " + type));
        // Cập nhật số tiền gửi tối thiều cho kỳ hạn
        term.setMinDeposit(termUpdateDto.minDeposit());
        // Cập nhật thời gian gởi tối thiểu để rút
        term.setDaysWithdrawn(termUpdateDto.minDepositTime());
        term.setInterestRate(termUpdateDto.interestRate());
        termRepository.save(term);
        System.out.println("-> Updated " + term);
    }
}
