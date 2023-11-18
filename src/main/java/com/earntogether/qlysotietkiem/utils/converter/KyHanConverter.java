package com.earntogether.qlysotietkiem.utils.converter;

import com.earntogether.qlysotietkiem.dto.KyHanDTO;
import com.earntogether.qlysotietkiem.entity.KyHan;

import java.math.BigInteger;

public class KyHanConverter {
    public static KyHan convertDTOtoEntity(KyHanDTO kyHanDto, int type) {
        int ngayDcRut = 15; // Áp dụng cho quy định 1
        var duocGuiThem = BigInteger.valueOf(0);// Chỉ áp dụng cho không kỳ hạn
        return KyHan.builder()
                .type(type)
                .name(kyHanDto.name())
                .month(kyHanDto.month())
                .laisuat(kyHanDto.laisuat())
                .minDeposit(kyHanDto.min_deposit())
                .ngayDcRut(ngayDcRut)
                .duocGuiThem(BigInteger.valueOf(0))
                .build();
    }
}
