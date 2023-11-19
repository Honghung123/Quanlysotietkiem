package com.earntogether.qlysotietkiem.utils.converter;

import com.earntogether.qlysotietkiem.dto.CustomerPassbookDTO;
import com.earntogether.qlysotietkiem.entity.Customer;

public class CustomerConverter {
    public static Customer covertDTOtoEntity(CustomerPassbookDTO cusPassbookDto){
        return Customer.builder()
                .customerCode(cusPassbookDto.customerCode())
                .name(cusPassbookDto.name())
                .address(cusPassbookDto.address())
                .identityNumber(cusPassbookDto.identityNumber())
                .build();
    }
}
