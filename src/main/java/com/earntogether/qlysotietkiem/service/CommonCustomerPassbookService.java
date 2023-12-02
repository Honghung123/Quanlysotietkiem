package com.earntogether.qlysotietkiem.service;

import com.earntogether.qlysotietkiem.entity.*;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.repository.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
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
    private TermRepository termRepository;
    private PassbookRepository passbookRepository;

    // CustomerService
    public void deleteCustomerByCustomerCode(int code) {
        customerRepository.deleteByCustomerCode(code).ifPresentOrElse(
                deletedCustomer -> {
                    updatePassbookStatus(deletedCustomer.getPassbookCode(), 0);
                    System.out.println("-> Deleted " + deletedCustomer);
                },
                () -> { throw new DataNotValidException("Không tồn tại " +
                        "khách hàng có makh : " + code);}
        );
    }

    public Optional<Customer> getCustomerByNameAndPassbookCode(String name,
                                                                int code){
        return customerRepository.findByNameAndPassbookCode(name, code);
    }

    public void updateMoneyByPassbookCode( @Positive int code,
                                           @NotNull BigInteger money){
        var passbook = passbookRepository.findByPassbookCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm " +
                        "thấy passbook có mã: " + code));
        passbook.setMoney(money);
        System.out.println(passbook);
        passbookRepository.save(passbook);
    }
    public String getNameByCustomerCode(int code) {
        var customer = customerRepository.findByCustomerCode(code).orElseThrow(
                () -> new ResourceNotFoundException("Không tìm thấy " +
                        "khách hàng có mã: " + code));
        return customer.getName();
    }

    // PassbookService
    public void updatePassbookStatus(int code, int status){
        Passbook passbook = passbookRepository.findByPassbookCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(
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

    // Passbook Service
    public BigInteger calculateInterestRate(Passbook passbook){
        // Tính số lần đáo hạn
        var term = passbook.getTerm();
        int monthsFromPassbookOpened = Math.toIntExact(ChronoUnit.MONTHS
                .between(passbook.getDateCreated(), LocalDate.now()));
        // Dùng floorDiv để lấy phần nguyên cho an toàn
        int timesMaturity = Math.floorDiv(monthsFromPassbookOpened,
                                        term.getMonthsOfTerm());
        double interestRate = (double)timesMaturity * term.getInterestRate()
                            * (double)term.getMonthsOfTerm()
                            * passbook.getMoney().doubleValue();
        return BigDecimal.valueOf(interestRate).toBigInteger();
    }
}
