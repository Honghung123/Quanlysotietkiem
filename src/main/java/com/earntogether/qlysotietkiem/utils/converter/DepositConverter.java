package com.earntogether.qlysotietkiem.utils.converter;

import com.earntogether.qlysotietkiem.dto.DepositSlipDTO;
import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.entity.DepositSlip;
import com.earntogether.qlysotietkiem.model.DepositSlipModel;
import com.earntogether.qlysotietkiem.model.WithdrawalSlipModel;

public class DepositConverter {
    public static DepositSlipModel convertEntityToModel(DepositSlip depositSlip) {
        return DepositSlipModel.builder()
                .id(depositSlip.getId())
                .customerCode(depositSlip.getCustomerCode())
                .passbookCode(depositSlip.getPassbookCode())
                .type(depositSlip.getType())
                .depositDate(depositSlip.getDepositDate())
                .money(depositSlip.getMoney())
                .build();
    }

    public static DepositSlip convertDTOtoEntity(DepositSlipDTO depositDto,
                                                 Customer customer) {
        return DepositSlip.builder()
                .passbookCode(depositDto.passbookCode())
                .customerCode(customer.getCustomerCode())
                .type(customer.getPassbook().getType())
                .depositDate(depositDto.depositDate())
                .money(depositDto.money())
                .build();
    }
}
