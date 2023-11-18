package com.earntogether.qlysotietkiem.utils.converter;

import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.model.PassbookModel;

public class PassBookConverter {

    public static PassbookModel convertEntityToModel(Customer customer) {
        return new PassbookModel(customer.getSotk().getMaso(),
                customer.getSotk().getType(),
                customer.getName(), customer.getSotk().getMoney());
    }
}
