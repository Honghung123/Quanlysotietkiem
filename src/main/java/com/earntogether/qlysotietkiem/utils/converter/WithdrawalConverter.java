package com.earntogether.qlysotietkiem.utils.converter;

import com.earntogether.qlysotietkiem.dto.WithdrawalSlipDTO;
import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.entity.WithdrawalSlip;
import com.earntogether.qlysotietkiem.model.DepositSlipModel;
import com.earntogether.qlysotietkiem.model.WithdrawalSlipModel;

public class WithdrawalConverter {

    public static WithdrawalSlipModel convertEntityToModel(WithdrawalSlip withdrawalSlip){
        return WithdrawalSlipModel.builder()
                .id(withdrawalSlip.getId())
                .customerCode(withdrawalSlip.getCustomerCode())
                .passbookCode(withdrawalSlip.getPassbookCode())
                .type(withdrawalSlip.getType())
                .withdrawalDate(withdrawalSlip.getWithdrawalDate())
                .money(withdrawalSlip.getMoney())
                .build();
    }

    public static WithdrawalSlip convertDTOtoEntity(WithdrawalSlipDTO withdrawalSlipDto,
                                                    Customer customer){
        return WithdrawalSlip.builder()
                .customerCode(customer.getCustomerCode())
                .passbookCode(customer.getPassbook().getPassbookCode())
                .type(customer.getPassbook().getType())
                .withdrawalDate(withdrawalSlipDto.withdrawalDate())
                .money(withdrawalSlipDto.money())
                .build();
    }
}
