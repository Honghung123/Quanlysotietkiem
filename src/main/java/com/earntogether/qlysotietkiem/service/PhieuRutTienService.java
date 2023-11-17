package com.earntogether.qlysotietkiem.service;

import com.earntogether.qlysotietkiem.dto.RutTienDTO;
import com.earntogether.qlysotietkiem.entity.Customer;
import com.earntogether.qlysotietkiem.entity.PhieuRutTien;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.model.RutTienModel;
import com.earntogether.qlysotietkiem.repository.PhieuRutTienRepository;
import com.earntogether.qlysotietkiem.repository.SoTietKiemRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class PhieuRutTienService {
    private PhieuRutTienRepository rutTienRepository;
    private CustomerService customerService;
    private SoTietKiemRepository sotkRepository;
    public List<RutTienModel> getAllPhieuRutTien(){
        return rutTienRepository.findAll().stream()
                .map(this::convertFromEntity).toList();
    }

    public RutTienModel convertFromEntity(PhieuRutTien rutTien){
        return RutTienModel.builder()
                .id(rutTien.getId())
                .makh(rutTien.getMakh())
                .name(customerService.getNameByMakh(rutTien.getMakh()))
                .maso(rutTien.getMaso())
                .type(rutTien.getType())
                .date(rutTien.getDate())
                .money(rutTien.getMoney())
                .build();
    }

    public String insert(RutTienDTO rutTienDto) {
        String message = "";
        var customerOptional = customerService.getCustomerByNameAndMaso(
                rutTienDto.getName(), rutTienDto.getMaso());
        if(customerOptional.isPresent()){
            Customer customer = customerOptional.get();
            var soTk = customer.getSotk();
            var kyhan = soTk.getKyHan();
            // Kiểm tra thời gian mở sổ có đủ điều kiện được rút
            var currentDate = LocalDate.now();
            if(rutTienDto.getDateTakeOut().isAfter(currentDate)){
                throw new DataNotValidException(400, "Ngày rút không được " +
                        "vượt quá ngày hiện tại");
            }
            var dateOpened = soTk.getDateCreated();
            boolean hasQualifiedToTakeOut = ChronoUnit.DAYS.between(dateOpened,
                    currentDate) >= kyhan.getNgayDcRut();
            if (!hasQualifiedToTakeOut){
                throw new DataNotValidException(400, "Bạn không thể rút tiền " +
                   "vì thời gian số ngày mở sổ của bạn chưa đủ " + kyhan.getNgayDcRut());
            }

            if(kyhan.getType() == 0){
                // Kiểm tra xem đã gửi ít nhất 1 tháng chưa

                // Kiểm tra xem số tiền rút với số dư hiện có
                if(rutTienDto.getMoney().compareTo(soTk.getMoney()) > 0){
                    throw new DataNotValidException(400, "Số dư trong tài " +
                            "khoản không đủ để rút");
                }
            }else{
                // Kiểm tra xem sổ đã quá kì hạn chưa
                boolean isOverDue = ChronoUnit.DAYS.between(dateOpened,
                        currentDate) >= kyhan.getMonth();
                if(!isOverDue){
                    throw new DataNotValidException(400, "Sổ vẫn chưa đáo " +
                            "hạn, không thể rút");
                }
                // Kiểm tra xem có rút hêt toàn bộ không
                if(rutTienDto.getMoney().compareTo(soTk.getMoney()) < 0){
                    throw new DataNotValidException(400, "Phải rút hết toàn " +
                            "bộ số dư");
                }else if(rutTienDto.getMoney().compareTo(soTk.getMoney()) > 0) {
                    throw new DataNotValidException(400, "Số tiền rút đã vượt" +
                            " quá số dư");
                }
            }
            var moneyLeft = soTk.getMoney().subtract(rutTienDto.getMoney());
            customerService.updateMoneyByMakh(customer.getMakh(), moneyLeft);
            message = "Rut thanh cong!!";
            // Nếu rút hết tiền thì đóng sổ - đóng luôn Customer :V
            if(moneyLeft.equals(BigInteger.valueOf(0))) {
                customerService.deleteCustomerByMakh(customer.getMakh());
                var soTkUpdate = sotkRepository.findByMaso(soTk.getMaso()).get();
                soTkUpdate.setStatus(0);
                sotkRepository.save(soTkUpdate);
                message = "Rut thanh cong!! Da dong so tiet kiem";
            }
            var rutTien = convertFromDto(rutTienDto, customer);
            rutTienRepository.save(rutTien);
            System.out.println(rutTien);
        }else{
            throw new ResourceNotFoundException( 404, "Không tồn tại khách " +
                    "hàng: " + rutTienDto.getName() + " có mã sổ: " + rutTienDto.getMaso());
        }
        return message;
    }

    public PhieuRutTien convertFromDto(RutTienDTO rutTienDto,
                                       Customer customer){
         return PhieuRutTien.builder()
                .maso(rutTienDto.getMaso())
                .makh(customer.getMakh())
                .type(customer.getSotk().getType())
                .date(rutTienDto.getDateTakeOut())
                .money(rutTienDto.getMoney())
                .build();
    }

    public BigInteger getSumMoneyByTypeAndDate(@NotNull int type,
                                               @NotNull LocalDate date) {
//        return rutTienRepository.sumMoneyByTypeAndDate(type, date).get().getMoney();
        List<PhieuRutTien> listPhieuRutTien = rutTienRepository
                .findByTypeAndDate(type, date);

        BigInteger sumMoney = listPhieuRutTien.stream()
                .map(PhieuRutTien::getMoney)
                .reduce(BigInteger.valueOf(0), BigInteger::add);
        System.out.println("Sum moneys: " + sumMoney);
        return sumMoney;
    }
}
