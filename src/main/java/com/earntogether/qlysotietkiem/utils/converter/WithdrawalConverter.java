package com.earntogether.qlysotietkiem.utils.converter;

import com.earntogether.qlysotietkiem.dto.WithdrawalSlipDTO;
import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.entity.WithdrawalSlip;
import com.earntogether.qlysotietkiem.model.DepositSlipModel;
public class WithdrawalConverter {

    public static DepositSlipModel convertEntityToModel(WithdrawalSlip rutTien){
        return DepositSlipModel.builder()
                .id(rutTien.getId())
                .makh(rutTien.getMakh())
                .maso(rutTien.getMaso())
                .type(rutTien.getType())
                .date(rutTien.getDate())
                .money(rutTien.getMoney())
                .build();
    }

    public static WithdrawalSlip convertDTOtoEntity(WithdrawalSlipDTO withdrawalSlipDto,
                                                    Customer customer){
        return WithdrawalSlip.builder()
                .maso(withdrawalSlipDto.getMaso())
                .makh(customer.getMakh())
                .type(customer.getSotk().getType())
                .date(withdrawalSlipDto.getDateTakeOut())
                .money(withdrawalSlipDto.getMoney())
                .build();
    }
}
