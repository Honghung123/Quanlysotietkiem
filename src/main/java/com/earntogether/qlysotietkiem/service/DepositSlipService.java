package com.earntogether.qlysotietkiem.service;

import com.earntogether.qlysotietkiem.dto.DepositSlipDTO;
import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.model.DepositSlipModel;
import com.earntogether.qlysotietkiem.repository.DepositSlipRepository;
import com.earntogether.qlysotietkiem.repository.PassbookRepository;
import com.earntogether.qlysotietkiem.utils.converter.DepositConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class DepositSlipService {
    private DepositSlipRepository depositSlipRepository;
    private PassbookRepository passbookRepository;
    private CommonCustomerPassbookService commonCusPassbookService;

    public List<DepositSlipModel> getAllDepositSlip(){
        return depositSlipRepository.findAll().stream()
                .map(DepositConverter::convertEntityToModel)
                .toList();
    }

    public void insertDepositSlip(DepositSlipDTO depositSlipDto) {
        var customer = commonCusPassbookService.getCustomerByNameAndPassbookCode(
                depositSlipDto.customerName(), depositSlipDto.passbookCode())
                .orElseThrow( () -> new ResourceNotFoundException(
                        "Không tồn tại khách hàng: " + depositSlipDto.customerName() +
                                " có mã sổ: " + depositSlipDto.passbookCode()));
        // Kiểm tra loại tiết kiệm
        var passbook = passbookRepository.findByPassbookCode(customer.getPassbookCode())
                .orElseThrow(() -> new ResourceNotFoundException( "Không " +
                        "tìm thấy passbook có mã: " + customer.getPassbookCode()));
        var term = passbook.getTerm();
        if(term.getType() != 0){
            throw new DataNotValidException("Chỉ chấp nhận gửi tiền " +
                        "đối với loại sổ không kỳ hạn");
        }
        if(depositSlipDto.money().compareTo(term.getMinDeposit()) < 0){
            throw new DataNotValidException("Số tiền gửi không được " +
              "nhỏ hơn số tiền gởi tối thiểu là " + term.getMinDeposit());
        }
        if(depositSlipDto.depositDate().isAfter(LocalDate.now())){
            throw new DataNotValidException("Ngày gửi không thể vượt" +
                        " quá ngày hiện tại");
        }

        var moneyAdded = passbook.getMoney().add(depositSlipDto.money());
        commonCusPassbookService.updateMoneyByPassbookCode(
                                    passbook.getPassbookCode(), moneyAdded);
        var depositSlip = DepositConverter.convertDTOtoEntity(depositSlipDto,
                                                              passbook);
        depositSlipRepository.save(depositSlip);
        System.out.println("-> Inserted " + depositSlip);
    }

    public void deleteAllDepositSlip() {
        depositSlipRepository.deleteAll();
    }
}
