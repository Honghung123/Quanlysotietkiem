package com.earntogether.qlysotietkiem.service;

import com.earntogether.qlysotietkiem.dto.CustomerPassbookDTO;
import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.entity.Passbook;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.model.PassbookModel;
import com.earntogether.qlysotietkiem.repository.CustomerRepository;
import com.earntogether.qlysotietkiem.repository.TermRepository;
import com.earntogether.qlysotietkiem.utils.converter.CustomerConverter;
import com.earntogether.qlysotietkiem.utils.converter.PassBookConverter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class CustomerService {
    private CustomerRepository customerRepository;
    private PassbookService passbookService;
    private TermRepository termRepository;

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public Customer getCustomerByCustomerCode(int code){
        var customer = customerRepository.findByCustomerCode(code).orElseThrow(
                () -> new ResourceNotFoundException("Khong ton tai" +
                        " khach hang co ma khach hang: " + code));
        return customer;
    }

    public void insertCustomerPassbook(CustomerPassbookDTO cusPassbookDto){
        // Get term instance by type
        var term = termRepository.findByType(cusPassbookDto.type())
                .orElseThrow(() -> new ResourceNotFoundException(404,
                    "Không tồn tại kỳ hạn chỉ định. Vui lòng tạo mới kỳ hạn"));
        // Check money sent if is lower than minimum deposit
        if(cusPassbookDto.money().compareTo(term.getMinDeposit()) < 0){
            throw new DataNotValidException("Số tiền gửi không được nhỏ " +
                    "hơn số tiền tối thiểu là " + term.getMinDeposit());
        }
        // Check if identity number is exist
        if(customerRepository.findByIdentityNumber(cusPassbookDto.identityNumber()).isPresent()){
            throw new DataNotValidException("Đã tồn tại khách hàng có " +
                    "Chứng minh nhân dân: " + cusPassbookDto.identityNumber());
        }
        // Check if the opened date's passbook is over than present
        if(cusPassbookDto.dateOpened().isAfter(LocalDate.now())){
            throw new DataNotValidException("Ngày mở sổ không được vượt" +
                    " quá ngày hiện tại") ;
        }

        Customer customer = CustomerConverter.covertDTOtoEntity(cusPassbookDto);
        // Tạo mã khách hàng cho Customer đăng kí mới
        customer.setCustomerCode(getNewCustomerCode());
        // Kiểm tra mã sổ tồn tại chưa
        int passbookCode = customer.getPassbookCode();
        passbookService.getPassbookByCode(passbookCode).ifPresent(
                passbook -> {throw new DataNotValidException("Đã tồn tại mã " +
                        "sổ tiết kiệm: "+ passbook.getPassbookCode());}
        );
        // Tạo sổ tiết kiệm
        var passbook = new Passbook(null, passbookCode,
                customer.getCustomerCode(), 1, term,
                cusPassbookDto.dateOpened(), cusPassbookDto.money());
        customerRepository.save(customer);
        passbookService.insertPassbook(passbook);
        System.out.println("-> Inserted " + customer);
    }

    private int getNewCustomerCode() {
        int newCode = 1;
        while(customerRepository.findByCustomerCode(newCode).isPresent()){
            newCode++;
        }
        return newCode;
    }
}
