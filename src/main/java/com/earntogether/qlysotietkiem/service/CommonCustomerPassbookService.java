package com.earntogether.qlysotietkiem.service;

import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.entity.Passbook;
import com.earntogether.qlysotietkiem.entity.DepositSlip;
import com.earntogether.qlysotietkiem.entity.WithdrawalSlip;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.repository.*;
import jakarta.validation.constraints.NotNull;
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
    private DepositSlipRepository goiTienRepository;
    private WithdrawalSlipRepository rutTienRepository;
    private KyHanRepository kyhanRepository;
    private PassbookRepository passbookRepository;

    // CustomerService
    public void deleteCustomerByMakh(int makh){
        var deletedCustomer = customerRepository.deleteByMakh(makh).orElseThrow(
                () -> new DataNotValidException(400, "Không tồn tại khách " +
                        "hàng có makh : " + makh));
        updateStatus(deletedCustomer.getSotk().getMaso(), 0);
    }

    public Optional<Customer> getCustomerByNameAndMaso(String name, int maso){
        return customerRepository.findByNameAndSotkMaso(name,maso);
    }

    public void updateMoneyByMakh( int makh, @NotNull BigInteger money){
        var customer = customerRepository.findByMakh(makh).orElseThrow(
                () -> new ResourceNotFoundException(404, "Không tồn tại khách " +
                        "hàng có mã: " + makh));
        var soTk = customer.getSotk();
        soTk.setMoney(money);
        customer.setSotk(soTk);
        System.out.println(customer);
        customerRepository.save(customer);
    }
    public String getNameByMakh(int makh) {
        var customer = customerRepository.findByMakh(makh).orElseThrow(
                () -> new ResourceNotFoundException(404, "Không tìm thấy " +
                        "khách hàng có mã: " + makh));
        return customer.getName();
    }

    // PassbookService
    public void updateStatus(int maso, int status){
        Passbook tietKiem = passbookRepository.findByMaso(maso).orElseThrow(
                () -> new ResourceNotFoundException(404,
                        "Không tìm thấy sổ tiết kiệm có maso " + maso));
        tietKiem.setStatus(status);
        passbookRepository.save(tietKiem);
    }

    // PhieuGoiTien Service
    public BigInteger getSumDepositMoney(int type, @NotNull LocalDate date) {
        List<DepositSlip> listDepositSlip = goiTienRepository
                .findByTypeAndDate(type, date);
        var sumMoney = listDepositSlip.stream()
                .map(DepositSlip::getMoney)
                .reduce(BigInteger.valueOf(0), BigInteger::add);
        System.out.println("Sum of deposited money: " + sumMoney);
        return sumMoney;
    }

    // PhieuRutTien Service
    public BigInteger getSumTakenOutMoney(int type, @NotNull LocalDate date) {
        List<WithdrawalSlip> listWithdrawalSlip = rutTienRepository
                .findByTypeAndDate(type, date);
        BigInteger sumMoney = listWithdrawalSlip.stream()
                .map(WithdrawalSlip::getMoney)
                .reduce(BigInteger.valueOf(0), BigInteger::add);
        System.out.println("Sum moneys: " + sumMoney);
        return sumMoney;
    }
}
