package com.earntogether.qlysotietkiem.service;

import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.entity.Passbook;
import com.earntogether.qlysotietkiem.entity.DepositSlip;
import com.earntogether.qlysotietkiem.entity.WithdrawalSlip;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.repository.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Mục đích: Tạo các method chung cho các Service, tránh việc class này gọi
 * phương thức class kia và class kia cũng gọi phương thức class này dẫn đến
 * lỗi "Relying upon circular references"
 * @Author:
 * @Version:
 * ...
 * */

@Service
@AllArgsConstructor
public class CommonCustomerPassbookService {
    private CustomerRepository customerRepository;
    private DepositSlipRepository depositSlipRepository;
    private WithdrawalSlipRepository withdrawalSlipRepository;
    private KyHanRepository kyhanRepository;
    private PassbookRepository passbookRepository;

    // CustomerService
    public void deleteCustomerByCustomerCode(int code){
        var deletedCustomer = customerRepository.deleteByCustomerCode(code).orElseThrow(
                () -> new DataNotValidException(400, "Không tồn tại khách " +
                        "hàng có makh : " + code));
        updateStatus(deletedCustomer.getPassbook().getPassbookCode(), 0);
        System.out.println("-> Deleted " + deletedCustomer);
    }

    public Optional<Customer> getCustomersByNameAndPassbookCode(String name,
                                                                int code){
        return customerRepository.findByNameAndPassbookPassbookCode(name, code);
    }

    public void updateMoneyByCustomerCode( @Positive int code,
                                           @NotNull BigInteger money){
        var customer = customerRepository.findByCustomerCode(code).orElseThrow(
                () -> new ResourceNotFoundException(404, "Không tồn tại khách " +
                        "hàng có mã: " + code));
        var passbook = customer.getPassbook();
        passbook.setMoney(money);
        customer.setPassbook(passbook);
        System.out.println(customer);
        customerRepository.save(customer);
    }
    public String getNameByCustomerCode(int code) {
        var customer = customerRepository.findByCustomerCode(code).orElseThrow(
                () -> new ResourceNotFoundException(404, "Không tìm thấy " +
                        "khách hàng có mã: " + code));
        return customer.getName();
    }

    // PassbookService
    public void updateStatus(int code, int status){
        Passbook passbook = passbookRepository.findByPassbookCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(404,
                        "Không tìm thấy sổ tiết kiệm có mã = " + code));
        passbook.setStatus(status);
        passbookRepository.save(passbook);
    }

    // DepositSlip Service
    public BigInteger getSumDepositMoney(int type, @NotNull LocalDate date) {
        List<DepositSlip> listDepositSlip = depositSlipRepository
                .findByTypeAndDepositDate(type, date);
        var sumMoney = listDepositSlip.stream()
                .map(DepositSlip::getMoney)
                .reduce(BigInteger.valueOf(0), BigInteger::add);
        return sumMoney;
    }

    // WithdrawalSlip Service
    public BigInteger getSumWithdrawalMoney(int type, @NotNull LocalDate date) {
        List<WithdrawalSlip> listWithdrawalSlip = withdrawalSlipRepository
                .findByTypeAndWithdrawalDate(type, date);
        var sumMoney = listWithdrawalSlip.stream()
                .map(WithdrawalSlip::getMoney)
                .reduce(BigInteger.valueOf(0), BigInteger::add);
        return sumMoney;
    }
}
