package com.earntogether.qlysotietkiem.service;

import com.earntogether.qlysotietkiem.dto.KyHanDTO;
import com.earntogether.qlysotietkiem.entity.KyHan;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.repository.KyHanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class KyHanService {
    @Autowired
    private KyHanRepository kyHanRepository;
    public List<KyHan> getAllKyHan(){
        return kyHanRepository.findAll();
    }

    public void addNewKyHan(KyHanDTO kyHanDto) {
        if(kyHanRepository.findByMonth(kyHanDto.month()).isPresent()){
            throw new DataNotValidException(400,
                    "Đã tồn tại kỳ hạn có " + kyHanDto.month() + " tháng");
        }
        if(kyHanRepository.findByName(kyHanDto.name()).isPresent()){
            throw new DataNotValidException(400,
                    "Đã tồn tại kỳ hạn có tên: " + kyHanDto.name());
        }
        KyHan kyHan = convertFromDTO(kyHanDto);
        System.out.println(kyHan);
        kyHanRepository.save(kyHan);
    }

    private KyHan convertFromDTO(KyHanDTO kyHanDto) {
        int ngayDcRut = 15; // Áp dụng cho quy định 1
        var duocGuiThem = BigInteger.valueOf(0);// Chỉ áp dụng cho không kỳ hạn
        int type = 1;
        while(kyHanRepository.findByType(type).isPresent()){
            type++;
        }
        return KyHan.builder()
                .type(type)
                .name(kyHanDto.name())
                .month(kyHanDto.month())
                .laisuat(kyHanDto.laisuat())
                .minDeposit(kyHanDto.min_deposit())
                .ngayDcRut(ngayDcRut)
                .duocGuiThem(BigInteger.valueOf(0))
                .build();
    }
}
