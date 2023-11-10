package com.earntogether.starter_project.controller;

import com.earntogether.starter_project.entity.Customer;
import com.earntogether.starter_project.service.CustomerService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    private MongoTemplate mongoTemplate;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomer(), HttpStatus.OK);
    }

    @GetMapping("/{makh}")
    public ResponseEntity<Customer> getCustomerByMakh(@PathVariable int makh){
        return new ResponseEntity<>(customerService.getCustomersByMakh(makh),
                HttpStatus.OK);
    }

    @PostMapping
    public void insertCusomer(@RequestBody Customer customer){
        Customer isExistCustomer =
                customerService.getCustomersByMakh(customer.getMakh());
        if(isExistCustomer == null){
            customerService.insertCustomer(customer);
        }else{
            System.out.println("Customer co makh " + customer.getMakh() + " " +
                    "ton tai");
        }
    }

    @PutMapping
    public void updateCustomer(@RequestBody Customer customer){
        if(customer != null){
            customerService.updateCustomer(customer);
        }
    }

    @DeleteMapping("/{makh}")
    public void deleteCustomerByMakh(@PathVariable int makh){
        customerService.deleteCustomerByMakh(makh);
    }

}
