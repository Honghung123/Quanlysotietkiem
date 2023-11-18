package com.earntogether.qlysotietkiem.service;

import com.earntogether.qlysotietkiem.dto.DepositSlipDTO;
import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.model.WithdrawalSlipModel;
import com.earntogether.qlysotietkiem.repository.DepositSlipRepository;
import com.earntogether.qlysotietkiem.utils.converter.DepositConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class DepositSlipService {
    private DepositSlipRepository goiTienRepository;
    private CommonCustomerPassbookService commonCusPassbookService;

    public List<WithdrawalSlipModel> getAllPhieuGoiTien(){
        return goiTienRepository.findAll().stream()
                .map(DepositConverter::convertEntityToModel)
                .toList();
    }

    public void insert(DepositSlipDTO guiTienDto) {
        Customer customer = commonCusPassbookService.getCustomerByNameAndMaso(
                guiTienDto.getName(), guiTienDto.getMaso())
                .orElseThrow( () -> new ResourceNotFoundException( 404,
                        "Không tồn tại khách hàng: " + guiTienDto.getName() +
                                " có mã: " + guiTienDto.getMaso()));
        // Kiểm tra loại tiết kiệm
        var passbook = customer.getSotk();
        if(passbook.getKyHan().getType() != 0){
            throw new DataNotValidException(400, "Chỉ chấp nhận gửi tiền " +
                        "đối với loại sổ không kỳ hạn");
        }
        if(guiTienDto.getMoney().compareTo(passbook.getKyHan().getMinDeposit()) < 0){
            throw new DataNotValidException(400, "Số tiền gửi không được " +
              "nhỏ hơn số tiền gởi tối thiểu là " + passbook.getKyHan().getMinDeposit());
        }
        if(guiTienDto.getDateSent().isAfter(LocalDate.now())){
            throw new DataNotValidException(400, "Ngày gửi không thể vượt" +
                        " quá ngày hiện tại");
        }

        var moneyAdded = passbook.getMoney().add(guiTienDto.getMoney());
        commonCusPassbookService.updateMoneyByMakh(customer.getMakh(), moneyAdded);
        var goiTien = DepositConverter.convertDTOtoEntity(guiTienDto, customer);
        goiTienRepository.save(goiTien);
        System.out.println(goiTien);
    }
}
