package com.earntogether.qlysotietkiem.utils.converter;

import com.earntogether.qlysotietkiem.dto.DepositSlipDTO;
import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.entity.DepositSlip;
import com.earntogether.qlysotietkiem.model.WithdrawalSlipModel;

public class DepositConverter {
    public static WithdrawalSlipModel convertEntityToModel(DepositSlip goiTien) {
        return WithdrawalSlipModel.builder()
                .id(goiTien.getId())
                .makh(goiTien.getMakh())
                .maso(goiTien.getMaso())
                .type(goiTien.getType())
                .date(goiTien.getDate())
                .money(goiTien.getMoney())
                .build();
    }

    public static DepositSlip convertDTOtoEntity(DepositSlipDTO guiTienDto,
                                                 Customer customer) {
        return DepositSlip.builder()
                .maso(guiTienDto.getMaso())
                .makh(customer.getMakh())
                .type(customer.getSotk().getType())
                .date(guiTienDto.getDateSent())
                .money(guiTienDto.getMoney())
                .build();
    }
}
