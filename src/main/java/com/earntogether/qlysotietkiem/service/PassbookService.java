package com.earntogether.qlysotietkiem.service;
import com.earntogether.qlysotietkiem.dto.ReportDTO;
import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.entity.Passbook;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.model.AccountingModel;
import com.earntogether.qlysotietkiem.model.PassbookModel;
import com.earntogether.qlysotietkiem.model.ReportModel;
import com.earntogether.qlysotietkiem.repository.TermRepository;
import com.earntogether.qlysotietkiem.repository.PassbookRepository;
import com.earntogether.qlysotietkiem.utils.converter.PassBookConverter;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class PassbookService {
    private PassbookRepository passbookRepository;
    private TermRepository kyhanRepository;
    private CommonCustomerPassbookService commonCustomerPassbookService;

    public List<Passbook> getAllPassbook(){
        return passbookRepository.findAll();
    }

    public Optional<Passbook> getPassbookByCode(int code) {
        return passbookRepository.findByPassbookCode(code);
    }

    public void insertPassbook(Passbook passbook) {
        passbookRepository.save(passbook);
    }

    public List<Integer> countOpenClosePassbook(int type,@NonNull LocalDate date){
        var listPassbookByDate =
                passbookRepository.findByTermTypeAndDateCreated(type, date);
        int numOfOpened = 0;
        int numOfClosed = 0;
        for(var passbook: listPassbookByDate){
            if(passbook.getStatus() == 1) numOfOpened ++;
            else if(passbook.getStatus() == 0) numOfClosed++;
        };
        return new LinkedList<>(Arrays.asList(numOfOpened, numOfClosed));
    }

    public List<ReportModel> getOpenClosePassbookMonthlyReport(ReportDTO reportDto) {
        // Lấy số ngày trong tháng năm chỉ định
        int totalDays = reportDto.monthYear().lengthOfMonth();
        int type = reportDto.type();
        List<ReportModel> monthlyReports = new LinkedList<>();
        for(int day = 1 ; day <= totalDays; day++){
            LocalDate date =  reportDto.monthYear().atDay(day);
            // Query database lấy số mở và đóng
            var numOpenClosePassbookList = countOpenClosePassbook(type, date);
            int numOfOpened = numOpenClosePassbookList.get(0);
            int numOfClosed = numOpenClosePassbookList.get(1);
            if(numOfOpened > 0 || numOfClosed > 0){
                monthlyReports.add(new ReportModel(day, numOfOpened,
                        numOfClosed));
            }
        }
        return monthlyReports;
    }

    public List<AccountingModel> getDailyTurnover(LocalDate date) {
        if(date.isAfter(LocalDate.now())){
            throw new DataNotValidException( "Ngày tra cứu không được " +
                    "vượt qua ngày hiện tại");
        }
        List<AccountingModel> revenueList = new LinkedList<>();
        var listKyHan = kyhanRepository.findAll();
        listKyHan.forEach(kyhan -> {
            int type = kyhan.getType();
            var sumOfMoneyDeposit =
                    commonCustomerPassbookService.getSumDepositMoney(type, date);
            var sumOfWithdrawalMoney = commonCustomerPassbookService
                                        .getSumWithdrawalMoney(type, date);
            revenueList.add(new AccountingModel(kyhan.getName(), type,
                    sumOfMoneyDeposit, sumOfWithdrawalMoney));
        });
        return revenueList;
    }

    public List<PassbookModel> lookupPassbooks() {
        return this.getAllPassbook().stream()
                .filter(passbook -> passbook.getStatus() == 1)
                .map(passbook -> {
                    String cusName = commonCustomerPassbookService
                            .getNameByCustomerCode(passbook.getCustomerCode());
                    var interestRate = commonCustomerPassbookService
                                    .calculateInterestRate(passbook);
                    passbook.setMoney(passbook.getMoney().add(interestRate));
                    return PassBookConverter.convertEntityToModel(passbook,
                            cusName);
                })
                .toList();
    }

    public Map<String, Object> lookupPassbooks(int page, int per_page,
                                               String sortBy) {
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, per_page, sort);
        Page<Passbook> passbookPages = this.getAllWithPagination(pageable);
        var passbookList = passbookPages.getContent();
        List<PassbookModel> passbookModelList = passbookList.stream()
                .filter(passbook -> passbook.getStatus() == 1)
                .map(passbook -> {
                    String cusName = commonCustomerPassbookService
                            .getNameByCustomerCode(passbook.getCustomerCode());
                    var interestRate = commonCustomerPassbookService
                            .calculateInterestRate(passbook);
                    passbook.setMoney(passbook.getMoney().add(interestRate));
                    return PassBookConverter.convertEntityToModel(passbook,
                            cusName);
                })
                .toList();
        Map<String, Object> cusPassbookMap = new LinkedHashMap<>();
        cusPassbookMap.put("data", passbookModelList);
        cusPassbookMap.put("total_pages", passbookPages.getTotalPages());
        cusPassbookMap.put("total_element", passbookPages.getTotalElements());
        cusPassbookMap.put("page", passbookPages.getNumber());
        return cusPassbookMap;
    }

    public Page<Passbook> getAllWithPagination(Pageable pageable) {
        return passbookRepository.findAll(pageable);
    }

    public void deleteAllPassbook() {
        passbookRepository.deleteAll();
    }


}
