package com.earntogether.starter_project.service;

import com.earntogether.starter_project.entity.Customer;
import com.earntogether.starter_project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public Customer getCustomersByMakh(int makh){
        return customerRepository.findByMakh(makh);
    }

    public void insertCustomer(Customer customer){
        customerRepository.save(customer);
    }

    public void updateCustomer(Customer customer){
        Query query = new Query();
        query.addCriteria(Criteria.where("makh").is(customer.getMakh()));
        Update update = new Update();
        update.set("name", customer.getName());
        update.set("address", customer.getAddress());
        update.set("cmnd", customer.getCmnd());
        mongoTemplate.updateFirst(query,update,Customer.class);
        System.out.println("Updated customer id: " + customer.getMakh());
    }

    public void deleteCustomerByMakh(int makh){
        customerRepository.deleteByMakh(makh);
    }
}
