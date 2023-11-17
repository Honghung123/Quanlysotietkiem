package com.earntogether.qlysotietkiem.service;

import com.earntogether.qlysotietkiem.dto.SoTietKiemDTO;
import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.entity.SoTietKiem;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.repository.CustomerRepository;
import com.earntogether.qlysotietkiem.repository.KyHanRepository;
import com.earntogether.qlysotietkiem.repository.SoTietKiemRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
@AllArgsConstructor
public class CustomerService {
//    @Autowired
    private CustomerRepository customerRepository;
    private SoTietKiemRepository sotkRepository;
    private KyHanRepository kyHanRepository;
//    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomersByMakh(int makh){
        Optional<Customer> customer = customerRepository.findByMakh(makh);
        if(customer.isEmpty()){
            throw new ResourceNotFoundException(404, "Khong ton tai khach " +
                    "hang co ma khach hang: " + makh);
        }
        return customer;
    }

    public void insertCustomer(SoTietKiemDTO soTkDto){
        Customer customer = covertFromDto(soTkDto);
        // Tạo makh cho Customer đăng kí mới
        int newMakh = 1;
        while(customerRepository.findByMakh(newMakh).isPresent()){
            newMakh++;
        }
        customer.setMakh(newMakh);
        // Ky han
        var kyhan = kyHanRepository.findByType(soTkDto.type()).get();
        if(kyhan == null){
            throw new ResourceNotFoundException(400, "Không tồn tại kỳ hạn có" +
                    " type = " + soTkDto.type());
        }
        // Tạo Mã sổ tiet kiem
        int maSotk = 1;
        while(sotkRepository.findByMaso(maSotk).isPresent()){
            maSotk++;
        }
        var newSotk = new SoTietKiem(null, maSotk,1, soTkDto.type(),
                soTkDto.dateOpened(), soTkDto.money(), null);
        newSotk.setKyHan(kyhan);
        // Check money sent
        var deposit = soTkDto.money();
        if(deposit.compareTo(kyhan.getMinDeposit()) < 0){
            throw new DataNotValidException(400, "Số tiền gửi không được nhỏ " +
                    "hơn số tiền tối thiểu là " + kyhan.getMinDeposit());
        }
        // Check ngày mở sổ không được quá ngày hiện tại
        if(soTkDto.dateOpened().isAfter(LocalDate.now())){
            throw new DataNotValidException(400, "Ngày mở sổ không được vượt" +
                    " quá ngày hiện tại") ;
        }
        customer.setSotk(newSotk);
        System.out.println(customer);

        customerRepository.save(customer);
        sotkRepository.save(customer.getSotk());
    }

    private Customer covertFromDto(SoTietKiemDTO soTkDto){
        return Customer.builder()
                .makh(soTkDto.makh())
                .name(soTkDto.name())
                .address(soTkDto.address())
                .cmnd(soTkDto.cmnd())
                .build();
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

    public Optional<Customer> getCustomerByNameAndMaso(String name, int maso){
        return customerRepository.findByNameAndSotkMaso(name,maso);
    }

    public void updateMoneyByMakh(@NotNull int makh,@NotNull BigInteger money){
        var customer = customerRepository.findByMakh(makh).get();
        if(customer == null){
            throw new ResourceNotFoundException(404, "Không tồn tại khách " +
                    "hàng có mã: " + makh);
        }
        var soTk = customer.getSotk();
        soTk.setMoney(money);
        customer.setSotk(soTk);
        System.out.println(customer);
        customerRepository.save(customer);
    }

    public Page<Customer> getAllWithPagination(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public void deleteSotkbyMakh(int makh) {
        Optional<Customer> customerOpt = customerRepository.findByMakh(makh);
        if(customerOpt.isPresent()){
            var customer = customerOpt.get();
            customer.setSotk(null);
            customerRepository.save(customer);
        }else{
            throw new ResourceNotFoundException(404, "Không tìm thấy khách " +
                    "hàng có mã: " + makh);
        }
    }

    public String getNameByMakh(int makh) {
        return customerRepository.findByMakh(makh).get().getName();
    }
}
