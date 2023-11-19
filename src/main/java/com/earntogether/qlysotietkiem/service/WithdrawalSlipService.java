package com.earntogether.qlysotietkiem.service;

import com.earntogether.qlysotietkiem.dto.WithdrawalSlipDTO;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.model.DepositSlipModel;
import com.earntogether.qlysotietkiem.model.WithdrawalSlipModel;
import com.earntogether.qlysotietkiem.repository.WithdrawalSlipRepository;
import com.earntogether.qlysotietkiem.utils.converter.WithdrawalConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class WithdrawalSlipService {
    private WithdrawalSlipRepository depositSlipRepository;
    private CommonCustomerPassbookService commonCusPassbookService;
    public List<WithdrawalSlipModel> getAllWithdrawalSlip(){
        return depositSlipRepository.findAll().stream()
                .map(WithdrawalConverter::convertEntityToModel).toList();
    }

    public String insertWithdrawalSlip(WithdrawalSlipDTO withdrawalSlipDto) {
        var customer = commonCusPassbookService.getCustomersByNameAndPassbookCode(
                withdrawalSlipDto.customerName(), withdrawalSlipDto.passbookCode())
                .orElseThrow(() -> new ResourceNotFoundException( 404,
                        "Không tồn tại khách hàng: " + withdrawalSlipDto.customerName()
                                + " có mã sổ: " + withdrawalSlipDto.passbookCode()));
        var passbook = customer.getPassbook();
        var kyhan = passbook.getKyHan();
        // Kiểm tra thời gian mở sổ có đủ điều kiện được rút
        var currentDate = LocalDate.now();
        if(withdrawalSlipDto.withdrawalDate().isAfter(currentDate)){
            throw new DataNotValidException(400, "Ngày rút không được " +
                        "vượt quá ngày hiện tại");
        }
        var dateOpened = passbook.getDateCreated();
        boolean hasQualifiedToTakeOut = ChronoUnit.DAYS.between(dateOpened,
                    currentDate) >= kyhan.getNgayDcRut();
        if (!hasQualifiedToTakeOut){
            throw new DataNotValidException(400, "Bạn không thể rút tiền " +
                "vì thời gian số ngày mở sổ của bạn chưa đủ " + kyhan.getNgayDcRut());
        }

        if(kyhan.getType() == 0){
            // Kiểm tra xem đã gửi ít nhất 1 tháng chưa

            // Kiểm tra xem số tiền rút với số dư hiện có
            if(withdrawalSlipDto.money().compareTo(passbook.getMoney()) > 0){
                throw new DataNotValidException(400, "Số dư trong tài " +
                            "khoản không đủ để rút");
            }
        }else{
            // Kiểm tra xem sổ đã quá kì hạn chưa
            boolean isOverDue = ChronoUnit.DAYS.between(dateOpened,
                        currentDate) >= kyhan.getNumOfMonths();
            if(!isOverDue){
                throw new DataNotValidException(400, "Sổ vẫn chưa đáo " +
                            "hạn, không thể rút");
            }
            // Kiểm tra xem có rút hêt toàn bộ không
            if(withdrawalSlipDto.money().compareTo(passbook.getMoney()) < 0){
                throw new DataNotValidException(400, "Phải rút hết toàn " +
                            "bộ số dư");
            }else if(withdrawalSlipDto.money().compareTo(passbook.getMoney()) > 0) {
                throw new DataNotValidException(400, "Số tiền rút đã vượt" +
                            " quá số dư");
            }
        }

        var moneyLeft = passbook.getMoney().subtract(withdrawalSlipDto.money());
        commonCusPassbookService.updateMoneyByCustomerCode(customer.getCustomerCode(), moneyLeft);
        String message = "Rut thanh cong!!";
        // Nếu rút hết tiền thì đóng sổ - đóng luôn Customer :V
        if(moneyLeft.equals(BigInteger.valueOf(0))) {
            commonCusPassbookService.deleteCustomerByCustomerCode(customer.getCustomerCode());
            message = "Rut thanh cong!! Da dong so tiet kiem";
        }
        var depositSlip = WithdrawalConverter.convertDTOtoEntity(
                withdrawalSlipDto, customer);
        depositSlipRepository.save(depositSlip);
        System.out.println("-> Inserted " + depositSlip);
        return message;
    }
}
