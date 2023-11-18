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
    private final CommonCustomerPassbookService commonCustomerPassbookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomer();
    }

    @GetMapping("/{makh}")
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerByMakh(@PathVariable int makh){
        return customerService.getCustomersByMakh(makh) ;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String insertCustomer(@Valid CustomerPassbookDTO soTkDto){
        customerService.insertCustomerPassbook(soTkDto);
        String message = "Created successfully";
        return String.format("{\"message\": \"%s\"}", message);
    }

    @DeleteMapping("/{makh}")
    public void deleteCustomerByMakh(@PathVariable int makh){
        commonCustomerPassbookService.deleteCustomerByMakh(makh);
    }

    @GetMapping("/tracuu")
    @ResponseStatus(HttpStatus.OK)
    public List<PassbookModel> traCuuSoTietKiem(){
        return customerService.traCuuSoTietKiem();
    }

    @GetMapping("/lookup")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> traCuuSoTietKiem(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "per_page", defaultValue = "2") int per_page,
            @RequestParam(name = "sortBy", defaultValue = "makh") String sortBy
    ){
        return customerService.traCuuSoTietKiem(page, per_page, sortBy);
    }

}
