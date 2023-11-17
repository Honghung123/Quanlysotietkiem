package com.earntogether.qlysotietkiem.service;

import com.earntogether.qlysotietkiem.dto.GoiTienDTO;
import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.entity.PhieuGoiTien;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.model.GoiTienModel;
import com.earntogether.qlysotietkiem.repository.PhieuGoiTienRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PhieuGoiTienService {
    private PhieuGoiTienRepository goiTienRepository;
    private CustomerService customerService;
    private MongoTemplate mongoTemplate;

    public List<GoiTienModel> getAllPhieuGoiTien(){
        return goiTienRepository.findAll().stream()
                .map(this::convertFromEntity).toList();
    }

    private GoiTienModel convertFromEntity(PhieuGoiTien goiTien) {
        return GoiTienModel.builder()
                .id(goiTien.getId())
                .makh(goiTien.getMakh())
                .name(customerService.getNameByMakh(goiTien.getMakh()))
                .maso(goiTien.getMaso())
                .type(goiTien.getType())
                .date(goiTien.getDate())
                .money(goiTien.getMoney())
                .build();
    }

    public List<PhieuGoiTien> getPhieuGoiTienByDate(){
        List<PhieuGoiTien> listPhieuGoiTien = goiTienRepository
                .findByTypeAndDate(1, LocalDate.parse("2023" +
                                "-11-11"));

        BigInteger sumMoney = listPhieuGoiTien.stream()
                .map(PhieuGoiTien::getMoney)
            .reduce(BigInteger.valueOf(0), BigInteger::add);
        System.out.println("Sum money: " + sumMoney);
        return listPhieuGoiTien;
    }

    public void insert(GoiTienDTO guiTienDto) {
        Optional<Customer> customerOptional =
                customerService.getCustomerByNameAndMaso(guiTienDto.getName(),
                        guiTienDto.getMaso());
        if(customerOptional.isPresent()){
            var customer = customerOptional.get();
            // Kiểm tra loại tiết kiệm
            var soTk = customer.getSotk();
            if(soTk.getKyHan().getType() != 0){
                throw new DataNotValidException(400, "Chỉ chấp nhận gửi tiền " +
                        "đối với loại sổ không kỳ hạn");
            }
            if(guiTienDto.getMoney().compareTo(soTk.getKyHan().getMinDeposit()) < 0){
                throw new DataNotValidException(400, "Số tiền gửi không được " +
                   "nhỏ hơn số tiền gởi tối thiểu là " + soTk.getKyHan().getMinDeposit());
            }
            if(guiTienDto.getDateSent().isAfter(LocalDate.now())){
                throw new DataNotValidException(400, "Ngày gửi không thể vượt" +
                        " quá ngày hiện tại");
            }
            var moneyAdded = soTk.getMoney().add(guiTienDto.getMoney());
            customerService.updateMoneyByMakh(customer.getMakh(), moneyAdded);

            var goiTien = convertFromDto(guiTienDto, customer);
//            goiTienRepository.save(goiTien);
            System.out.println(goiTien);
        }else{
            throw new ResourceNotFoundException( 404, "Không tồn tại khách " +
                    "hàng: " + guiTienDto.getName() + " có mã: " + guiTienDto.getMaso());
        }
    }

    private static PhieuGoiTien convertFromDto(GoiTienDTO guiTienDto,
                                               Customer customer) {
        return PhieuGoiTien.builder()
                .maso(guiTienDto.getMaso())
                .makh(customer.getMakh())
                .type(customer.getSotk().getType())
                .date(guiTienDto.getDateSent())
                .money(guiTienDto.getMoney())
                .build();
    }

    public BigInteger getSumMoneyByTypeAndDate(int type, LocalDate date) {
//        goiTienRepository.sumMoneyByTypeAndDate(type, date.toString()).getMappedResults()
//                .forEach(System.out::println);
        List<PhieuGoiTien> listPhieuGoiTien = goiTienRepository
                .findByTypeAndDate(type, date);

        BigInteger sumMoney = listPhieuGoiTien.stream()
                .map(PhieuGoiTien::getMoney)
                .reduce(BigInteger.valueOf(0), BigInteger::add);
        System.out.println("Sum money: " + sumMoney);
        return sumMoney;
    }

}
