package com.earntogether.qlysotietkiem.service;

import com.earntogether.qlysotietkiem.dto.DepositSlipDTO;
import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.model.DepositSlipModel;
import com.earntogether.qlysotietkiem.repository.DepositSlipRepository;
import com.earntogether.qlysotietkiem.utils.converter.DepositConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class DepositSlipService {
    private DepositSlipRepository depositSlipRepository;
    private CommonCustomerPassbookService commonCusPassbookService;

    public List<DepositSlipModel> getAllDepositSlip(){
        return depositSlipRepository.findAll().stream()
                .map(DepositConverter::convertEntityToModel)
                .toList();
    }

    public void insertDepositSlip(DepositSlipDTO depositSlipDto) {
        Customer customer =
                commonCusPassbookService.getCustomersByNameAndPassbookCode(
                depositSlipDto.customerName(), depositSlipDto.passbookCode())
                .orElseThrow( () -> new ResourceNotFoundException( 404,
                        "Không tồn tại khách hàng: " + depositSlipDto.customerName() +
                                " có mã sổ: " + depositSlipDto.passbookCode()));
        // Kiểm tra loại tiết kiệm
        var passbook = customer.getPassbook();
        if(passbook.getTerm().getType() != 0){
            throw new DataNotValidException(400, "Chỉ chấp nhận gửi tiền " +
                        "đối với loại sổ không kỳ hạn");
        }
        if(depositSlipDto.money().compareTo(passbook.getTerm().getMinDeposit()) < 0){
            throw new DataNotValidException(400, "Số tiền gửi không được " +
              "nhỏ hơn số tiền gởi tối thiểu là " + passbook.getTerm().getMinDeposit());
        }
        if(depositSlipDto.depositDate().isAfter(LocalDate.now())){
            throw new DataNotValidException(400, "Ngày gửi không thể vượt" +
                        " quá ngày hiện tại");
        }

        var moneyAdded = passbook.getMoney().add(depositSlipDto.money());
        commonCusPassbookService.updateMoneyByCustomerCode(
                                    customer.getCustomerCode(), moneyAdded);
        var depositSlip = DepositConverter.convertDTOtoEntity(depositSlipDto,
                                                              customer);
        depositSlipRepository.save(depositSlip);
        System.out.println("-> Inserted " + depositSlip);
    }
}
