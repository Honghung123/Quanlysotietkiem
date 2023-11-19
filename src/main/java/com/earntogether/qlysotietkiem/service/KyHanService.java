package com.earntogether.qlysotietkiem.service;

import com.earntogether.qlysotietkiem.dto.KyHanDTO;
import com.earntogether.qlysotietkiem.dto.KyHanUpdateDTO;
import com.earntogether.qlysotietkiem.entity.KyHan;
import com.earntogether.qlysotietkiem.exception.DataNotValidException;
import com.earntogether.qlysotietkiem.exception.ResourceNotFoundException;
import com.earntogether.qlysotietkiem.repository.KyHanRepository;
import com.earntogether.qlysotietkiem.utils.converter.KyHanConverter;
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

    public void insertKyHan(KyHanDTO kyHanDto) {
        if(kyHanRepository.findByNumOfMonths(kyHanDto.numOfMonths()).isPresent()){
            throw new DataNotValidException(400,
                    "Đã tồn tại kỳ hạn có " + kyHanDto.numOfMonths() + " tháng");
        }
        if(kyHanRepository.findByName(kyHanDto.name()).isPresent()){
            throw new DataNotValidException(400,
                    "Đã tồn tại kỳ hạn có tên: " + kyHanDto.name());
        }
        int type = this.generateNewType();
        KyHan newKyhan = KyHanConverter.convertDTOtoEntity(kyHanDto, type);
        kyHanRepository.save(newKyhan);
        System.out.println("-> Inserted " + newKyhan);
    }

    private int generateNewType() {
        int newType = 1;
        while(kyHanRepository.findByType(newType).isPresent()){
            newType++;
        }
        return newType;
    }

    public void deleteByType(int type){
        kyHanRepository.deleteByType(type);
        System.out.println("Deleted ky han co type = " + type);
    }

    public KyHan getKyHanByType(int type) {
        return kyHanRepository.findByType(type).orElseThrow(
                () -> new ResourceNotFoundException(404, "Không tồn tại kỳ " +
                        "hạn có type = " + type));
    }

    public void updateKyHan(KyHanUpdateDTO kyHanUpdateDto) {
        int type = kyHanUpdateDto.type();
        var kyhan = kyHanRepository.findByType(type).orElseThrow(
                () -> new ResourceNotFoundException(404, "Không tìm thấy kỳ " +
                        "hạn có type = " + type));
        kyhan.setMinDeposit(kyHanUpdateDto.minDeposit());
//      if(type == 0){
//           kyhan.setMinDateSent(kyHanUpdateDto.minDay());
//      }
        kyhan.setInterestRate(kyHanUpdateDto.interestRate());
        kyHanRepository.save(kyhan);
        System.out.println("-> Updated " + kyhan);
    }
}
