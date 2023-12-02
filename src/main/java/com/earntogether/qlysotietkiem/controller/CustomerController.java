package com.earntogether.qlysotietkiem.controller;

import com.earntogether.qlysotietkiem.dto.CustomerPassbookDTO;
import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.model.PassbookModel;
import com.earntogether.qlysotietkiem.service.CommonCustomerPassbookService;
import com.earntogether.qlysotietkiem.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/customer-passbook")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CommonCustomerPassbookService commonCusPassbookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomer();
    }

    @GetMapping("/{code}")
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerByCustomerCode(@PathVariable int code){
        return customerService.getCustomerByCustomerCode(code) ;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String insertCustomer(@Valid CustomerPassbookDTO cusPassbookDto){
        customerService.insertCustomerPassbook(cusPassbookDto);
        String message = "Created successfully";
        return String.format("{\"message\": \"%s\"}", message);
    }

    @DeleteMapping("/{code}")
    public void deleteCustomer(@PathVariable int code){
        commonCusPassbookService.deleteCustomerByCustomerCode(code);
    }
}
