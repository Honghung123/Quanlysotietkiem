package com.earntogether.qlysotietkiem.utils.converter;

import com.earntogether.qlysotietkiem.dto.TermInsertDTO;
import com.earntogether.qlysotietkiem.entity.Term;

import java.math.BigInteger;

public class TermConverter {
    public static Term convertDTOtoEntity(TermInsertDTO termInsertDto, int type) {
        int monthsOfTerm = termInsertDto.monthsOfTerm();
        String termName = monthsOfTerm == 0 ? "Không kỳ hạn"
                : String.format("%d tháng", monthsOfTerm);
        // Mặc định cho tất cả kỳ hạn
        int daysWithdrawn = 15;
        // Chỉ áp dụng cho không kỳ hạn
        var minAdditionalDeposit = monthsOfTerm == 0 ?
                BigInteger.valueOf(100000) : BigInteger.valueOf(0);
        // Chỉ áp dụng cho không kỳ hạn(đơn vị: tháng) - tối thiểu 1 tháng
        int minDespositTime = monthsOfTerm == 0 ? 1 : 0;

        return Term.builder()
                .type(type)
                .name(termName)
                .monthsOfTerm(termInsertDto.monthsOfTerm())
                .interestRate(termInsertDto.interestRate())
                .minDeposit(termInsertDto.minDeposit())
                .minAdditionalDeposit(minAdditionalDeposit)
                .daysWithdrawn(daysWithdrawn)
                .minDepositTime(minDespositTime)
                .build();
    }
}
