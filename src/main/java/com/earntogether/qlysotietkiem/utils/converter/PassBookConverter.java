package com.earntogether.qlysotietkiem.utils.converter;

import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.model.PassbookModel;

public class PassBookConverter {

    public static PassbookModel convertEntityToModel(Customer customer) {
        return new PassbookModel(customer.getPassbook().getPassbookCode(),
                customer.getPassbook().getType(),
                customer.getName(),
                customer.getPassbook().getMoney());
    }
}
