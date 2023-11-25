package com.earntogether.qlysotietkiem.utils.converter;

import com.earntogether.qlysotietkiem.dto.TermInsertDTO;
import com.earntogether.qlysotietkiem.entity.Term;

import java.math.BigInteger;

public class TermConverter {
    public static Term convertDTOtoEntity(TermInsertDTO termInsertDto, int type) {
        int monthsOfTerm = termInsertDto.monthsOfTerm();
        String termName = (monthsOfTerm == 0) ? "Không kỳ hạn"
                                    : String.format("%d tháng", monthsOfTerm);
        // Mặc định cho tất cả kỳ hạn
        int daysWithdrawn = 15;
        // Chỉ áp dụng cho không kỳ hạn
        var minAdditionalDeposit = monthsOfTerm == 0 ?
                BigInteger.valueOf(100000) : BigInteger.valueOf(0);
        // Mặc định số tháng của Không kì hạn(monthsOfTerm) là 1
        return Term.builder()
                .type(type)
                .name(termName)
                .monthsOfTerm(monthsOfTerm == 0 ? monthsOfTerm + 1 : monthsOfTerm)
                .interestRate(termInsertDto.interestRate())
                .minDeposit(termInsertDto.minDeposit())
                .minAdditionalDeposit(minAdditionalDeposit)
                .daysWithdrawn(daysWithdrawn)
                .build();
    }
}
