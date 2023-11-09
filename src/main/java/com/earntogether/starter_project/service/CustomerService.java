package com.earntogether.starter_project.service;

import com.earntogether.starter_project.entity.Customer;
import com.earntogether.starter_project.repository.CustomerRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public Customer getCustomersByMakh(ObjectId id){
        return customerRepository.findById(id).get() ;
    }
}
