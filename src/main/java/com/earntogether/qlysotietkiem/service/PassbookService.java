package com.earntogether.qlysotietkiem.service;
import com.earntogether.qlysotietkiem.dto.ReportDTO;
import com.earntogether.qlysotietkiem.entity.Passbook;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.model.AccountingModel;
import com.earntogether.qlysotietkiem.model.ReportModel;
import com.earntogether.qlysotietkiem.repository.KyHanRepository;
import com.earntogether.qlysotietkiem.repository.PassbookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class PassbookService {
    private PassbookRepository passbookRepository;
    private KyHanRepository kyhanRepository;
    private CommonCustomerPassbookService commonCustomerPassbookService;

    public List<Passbook> getAll(){
        return passbookRepository.findAll();
    }

    public int getNewMaSo() {
        int newMaso = 1;
        while(passbookRepository.findByMaso(newMaso).isPresent()){
            newMaso++;
        }
        return newMaso;
    }

    public void insert(Passbook passbook) {
        passbookRepository.save(passbook);
    }

    public void updateStatus(int maso, int status){
        Passbook tietKiem = passbookRepository.findByMaso(maso).orElseThrow(
                () -> new ResourceNotFoundException(404,
                        "Không tìm thấy sổ tiết kiệm có maso " + maso));
        tietKiem.setStatus(status);
        passbookRepository.save(tietKiem);
    }

    public List<Integer> countOpenClosePassbook(int type, LocalDate date){
        var listSotkByDate =
                passbookRepository.findByTypeAndDateCreated(type, date);
        int numOfOpened = 0;
        int numOfClosed = 0;
        for(var soTk: listSotkByDate){
            if(soTk.getStatus() == 1) numOfOpened ++;
            else if(soTk.getStatus() == 0) numOfClosed++;
        };
        return new LinkedList<>(Arrays.asList(numOfOpened, numOfClosed));
    }

    public List<ReportModel> getOpenClosePassbookReportByMonth(ReportDTO reportDto) {
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
            throw new DataNotValidException(400, "Ngày tra cứu không được " +
                    "vượt qua ngày hiện tại");
        }
        List<AccountingModel> revenueList = new LinkedList<>();
        var listKyHan = kyhanRepository.findAll();
        listKyHan.forEach(kyhan -> {
            int type = kyhan.getType();
            var sumOfMoneyDeposit =
                    commonCustomerPassbookService.getSumDepositMoney(type, date);
            var sumOfExpenseMoney =
                    commonCustomerPassbookService.getSumTakenOutMoney(type, date);
            revenueList.add(new AccountingModel(kyhan.getName(), type,
                    sumOfMoneyDeposit, sumOfExpenseMoney));
        });
        System.out.println(revenueList);
        return revenueList;
    }
}
