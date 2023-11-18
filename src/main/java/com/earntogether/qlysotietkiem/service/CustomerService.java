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
    private CommonCustomerPassbookService commonCusPassbookService;
    private PassbookService passbookService;
    private KyHanRepository kyhanRepository;
    private MongoTemplate mongoTemplate;

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public Customer getCustomersByMakh(int makh){
        var customer = customerRepository.findByMakh(makh).orElseThrow(
                () -> new ResourceNotFoundException(404, "Khong ton tai" +
                " khach hang co ma khach hang: " + makh));
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
        // Check if the opened date's passbook is over than present
        if(cusPassbookDto.dateOpened().isAfter(LocalDate.now())){
            throw new DataNotValidException(400, "Ngày mở sổ không được vượt" +
                    " quá ngày hiện tại") ;
        }

        Customer customer = CustomerConverter.covertDTOtoEntity(cusPassbookDto);
        // Tạo makh cho Customer đăng kí mới
        customer.setMakh(getNewMakh());
        // Tạo Mã sổ tiet kiem
        int maSotk = passbookService.getNewMaSo();
        var passbook = new Passbook(null, maSotk,1, cusPassbookDto.type(),
                cusPassbookDto.dateOpened(), cusPassbookDto.money(), kyhan);
        customer.setSotk(passbook);
        System.out.println(customer);

        customerRepository.save(customer);
        passbookService.insert(passbook);
    }

    private int getNewMakh() {
        int newMakh = 1;
        while(customerRepository.findByMakh(newMakh).isPresent()){
            newMakh++;
        }
        return newMakh;
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

    public void deleteSotkbyMakh(int makh) {
        var customer = customerRepository.findByMakh(makh).orElseThrow(
                () -> new ResourceNotFoundException(404, "Không tìm thấy " +
                        "khách hàng có mã: " + makh));
        passbookService.updateStatus(customer.getSotk().getMaso(), 0);
        customer.setSotk(null);
        customerRepository.save(customer);
    }
    public List<PassbookModel> traCuuSoTietKiem() {
        List<Customer> customers = this.getAllCustomer();
        return customers.stream()
                .filter(customer -> customer.getSotk()!=null)
                .map(PassBookConverter::convertEntityToModel)
                .toList();
    }

    public Map<String, Object> traCuuSoTietKiem(int page, int per_page,
                                                String sortBy) {
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, per_page, sort);
        Page<Customer> customerPages = this.getAllWithPagination(pageable);
        var customerList = customerPages.getContent();
        List<PassbookModel> soTkList = customerList.stream()
                .filter(customer -> customer.getSotk() != null)
                .map(PassBookConverter::convertEntityToModel)
                .toList();
        Map<String, Object> cusSoTkMap = new LinkedHashMap<>();
        cusSoTkMap.put("data", soTkList);
        cusSoTkMap.put("total_pages", customerPages.getTotalPages());
        cusSoTkMap.put("total_element", customerPages.getTotalElements());
        cusSoTkMap.put("page", customerPages.getNumber());
        return cusSoTkMap;
    }

    public Page<Customer> getAllWithPagination(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }
}
