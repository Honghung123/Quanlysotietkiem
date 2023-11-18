package com.earntogether.qlysotietkiem.utils.converter;

import com.earntogether.qlysotietkiem.dto.CustomerPassbookDTO;
import com.earntogether.qlysotietkiem.entity.Customer;

public class CustomerConverter {
    public static Customer covertDTOtoEntity(CustomerPassbookDTO cusPassbookDto){
        return Customer.builder()
                .makh(cusPassbookDto.makh())
                .name(cusPassbookDto.name())
                .address(cusPassbookDto.address())
                .cmnd(cusPassbookDto.cmnd())
                .build();
    }
}
