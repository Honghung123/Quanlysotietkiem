package com.earntogether.qlysotietkiem.controller;

import com.earntogether.qlysotietkiem.dto.SoTietKiemDTO;
import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/customer")
@CrossOrigin(origins = "*")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomer();
    }

    @GetMapping("/{makh}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Customer> getCustomerByMakh(@PathVariable @NotNull int makh){
        return customerService.getCustomersByMakh(makh) ;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String insertCustomer(@Valid SoTietKiemDTO soTkDto){
        customerService.insertCustomer(soTkDto);
        String message = "Created successfully";
        return String.format("{\"message\": \"%s\"}", message);
    }

    @PutMapping
    public void updateCustomer(@RequestBody @Valid Customer customer){
        if(customer != null){
            customerService.updateCustomer(customer);
        }
    }

    @DeleteMapping("/{makh}")
    public void deleteCustomerByMakh(@PathVariable @NotNull int makh){
        customerService.deleteCustomerByMakh(makh);
    }

}
