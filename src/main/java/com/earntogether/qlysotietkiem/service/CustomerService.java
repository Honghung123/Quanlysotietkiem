package com.earntogether.qlysotietkiem.service;

import com.earntogether.qlysotietkiem.dto.CustomerPassbookDTO;
import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.entity.Passbook;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.model.PassbookModel;
import com.earntogether.qlysotietkiem.repository.CustomerRepository;
import com.earntogether.qlysotietkiem.repository.KyHanRepository;
import com.earntogether.qlysotietkiem.utils.converter.CustomerConverter;
import com.earntogether.qlysotietkiem.utils.converter.PassBookConverter;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class CustomerService {
    private CustomerRepository customerRepository;
    private PassbookService passbookService;
    private KyHanRepository kyhanRepository;

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public Customer getCustomerByCustomerCode(int code){
        var customer = customerRepository.findByCustomerCode(code).orElseThrow(
                () -> new ResourceNotFoundException(404, "Khong ton tai" +
                " khach hang co ma khach hang: " + code));
        return customer;
    }

    public void insertCustomerPassbook(CustomerPassbookDTO cusPassbookDto){
        // Get kyhan instance by type
        var kyhan =
                kyhanRepository.findByType(cusPassbookDto.type()).orElseThrow(
                () -> new ResourceNotFoundException(400, "Không tồn tại " +
                        "kỳ hạn có type = " + cusPassbookDto.type()));
        // Check money sent if is lower than minimum deposit
        if(cusPassbookDto.money().compareTo(kyhan.getMinDeposit()) < 0){
            throw new DataNotValidException(400, "Số tiền gửi không được nhỏ " +
                    "hơn số tiền tối thiểu là " + kyhan.getMinDeposit());
        }
        // Check if identity number is exist
        if(customerRepository.findByIdentityNumber(cusPassbookDto.identityNumber()).isPresent()){
            throw new DataNotValidException(400, "Đã tồn tại khách hàng có " +
                    "Chứng minh nhân dân: " + cusPassbookDto.identityNumber());
        }
        // Check if the opened date's passbook is over than present
        if(cusPassbookDto.dateOpened().isAfter(LocalDate.now())){
            throw new DataNotValidException(400, "Ngày mở sổ không được vượt" +
                    " quá ngày hiện tại") ;
        }

        Customer customer = CustomerConverter.covertDTOtoEntity(cusPassbookDto);
        // Tạo makh cho Customer đăng kí mới
        customer.setCustomerCode(getNewCustomerCode());
        // Tạo Mã sổ tiet kiem
        int newPassbookCode = passbookService.getNewPassbookCode();
        var passbook = new Passbook(null, newPassbookCode,1,
                cusPassbookDto.type(), cusPassbookDto.dateOpened(),
                cusPassbookDto.money(), kyhan);
        customer.setPassbook(passbook);
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

    public void deletePassbookByCustomerCode(int code) {
        var customer = customerRepository.findByCustomerCode(code).orElseThrow(
                () -> new ResourceNotFoundException(404, "Không tìm thấy " +
                        "khách hàng có mã: " + code));
        int passbookCode = customer.getPassbook().getPassbookCode();
        passbookService.updateStatus(passbookCode ,0);
        customer.setPassbook(null);
        customerRepository.save(customer);
    }
    public List<PassbookModel> lookupPassbooks() {
        List<Customer> customers = this.getAllCustomer();
        return customers.stream()
                .filter(customer -> customer.getPassbook()!=null)
                .map(PassBookConverter::convertEntityToModel)
                .toList();
    }

    public Map<String, Object> lookupPassbooks(int page, int per_page,
                                                String sortBy) {
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, per_page, sort);
        Page<Customer> customerPages = this.getAllWithPagination(pageable);
        var customerList = customerPages.getContent();
        List<PassbookModel> soTkList = customerList.stream()
                .filter(customer -> customer.getPassbook() != null)
                .map(PassBookConverter::convertEntityToModel)
                .toList();
        Map<String, Object> cusPassbookMap = new LinkedHashMap<>();
        cusPassbookMap.put("data", soTkList);
        cusPassbookMap.put("total_pages", customerPages.getTotalPages());
        cusPassbookMap.put("total_element", customerPages.getTotalElements());
        cusPassbookMap.put("page", customerPages.getNumber());
        return cusPassbookMap;
    }

    public Page<Customer> getAllWithPagination(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }
}
