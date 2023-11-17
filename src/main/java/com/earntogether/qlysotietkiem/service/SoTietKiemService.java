package com.earntogether.qlysotietkiem.service;
import com.earntogether.qlysotietkiem.dto.ReportDTO;
import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.entity.SoTietKiem;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.model.KeToanNgay;
import com.earntogether.qlysotietkiem.model.ReportModel;
import com.earntogether.qlysotietkiem.model.SoTietKiemModel;
import com.earntogether.qlysotietkiem.repository.SoTietKiemRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class SoTietKiemService {
    private CustomerService customerService;
    private PhieuGoiTienService goiTienService;
    private PhieuRutTienService rutTienService;
    private KyHanService kyHanService;
    private SoTietKiemRepository tietKiemRepository;
//    }
    public void updateStatus(int maso, int status){
        SoTietKiem tietKiem = tietKiemRepository.findByMaso(maso).get();
        if(tietKiem == null){
            throw new ResourceNotFoundException(404, "Không tìm thấy sổ tiết " +
                    "kiệm có maso " + maso);
        }
        tietKiem.setStatus(status);
        tietKiemRepository.save(tietKiem);
    }

    public List<SoTietKiem> getAll(){
        return tietKiemRepository.findAll();
    }

    public List<Long> getAllPassbookInDay(LocalDate date){
        var listSotkByDate = tietKiemRepository.findByDateCreated(date);
        long numOfOpened = listSotkByDate.stream()
                        .filter(soTk ->soTk.getStatus() == 1).count();
        long numOfClosed = listSotkByDate.stream()
                .filter(soTk ->soTk.getStatus() == 0).count();
        return new LinkedList<>(Arrays.asList(numOfOpened, numOfClosed));
    }

    public List<KeToanNgay> getAvernueByDate(LocalDate date) {
        if(date == null) return null;
        if(date.isAfter(LocalDate.now())){
            throw new DataNotValidException(400, "Ngày tra cứu không được " +
                    "vượt qua ngày hiện tại");
        }

        List<KeToanNgay> avernueList = new LinkedList<>();
        var listKyHan = kyHanService.getAllKyHan();
        listKyHan.forEach(kyhan -> {
            int type = kyhan.getType();
            var sumOfMoney = goiTienService.getSumMoneyByTypeAndDate(type,
                    date);
            var sumOfExpenseMoney =
                    rutTienService.getSumMoneyByTypeAndDate(type, date);
            avernueList.add(new KeToanNgay(kyhan.getName(), type,  sumOfMoney
                    , sumOfExpenseMoney));
        });
        System.out.println(avernueList);
        return avernueList;
    }

    public List<SoTietKiemModel> searchAll() {
        List<Customer> customers = customerService.getAllCustomer();
        List<SoTietKiemModel> listSoTkInfo = new LinkedList<>();
        customers.stream().filter(customer -> customer.getSotk()!=null)
                .forEach(customer -> {
            var soTk = customer.getSotk();
            var soTkInfo = new SoTietKiemModel(soTk.getMaso(), soTk.getType(),
                    customer.getName(), soTk.getMoney());
            listSoTkInfo.add(soTkInfo);
        });
        return listSoTkInfo;
    }

    public Map<String, Object> tracuu(int page, int  per_page,
                                       String sortBy) {
        Map<String, Object> customerSotkList = new LinkedHashMap<>();
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, per_page, sort);
        Page<Customer> customerList =
                customerService.getAllWithPagination(pageable);
        customerSotkList.put("data", customerList.getContent());
        customerSotkList.put("total_pages", customerList.getTotalPages());
        customerSotkList.put("total_element", customerList.getTotalElements());
        customerSotkList.put("page", customerList.getNumber());
        return customerSotkList;
    }

    public List<ReportModel> getReportByMonth(int type, String monthYear) {
        if(!Pattern.matches("\\d{4}-\\d{2}", monthYear)){
            throw new DataNotValidException(400, "Tháng năm không hợp lệ");
        }
        var time = monthYear.split("-");
        int year = Integer.parseInt(time[0]);
        int month = Integer.parseInt(time[1]);
        // Lấy số ngày trong tháng năm chỉ định
        int totalDays = YearMonth.of(year,month).lengthOfMonth();
        List<ReportModel> reports = new LinkedList<>();
        for(int day = 1 ; day <= totalDays; day++){
            String dayStr = day < 10? "-0"+day : "-"+day;
            LocalDate date = LocalDate.parse(monthYear + dayStr);
            // Query database lấy số mở và đóng
            var numOpenClose = getAllPassbookInDay(date);
            long numOfOpened = numOpenClose.get(0);
            long numOfClosed = numOpenClose.get(1);
            if(numOfOpened > 0 || numOfClosed > 0){
                reports.add(new ReportModel(day, numOfOpened, numOfClosed));
            }
        }
        return reports;
    }
}
