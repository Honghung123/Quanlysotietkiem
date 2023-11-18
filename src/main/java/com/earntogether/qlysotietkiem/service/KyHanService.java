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

    public void addNewKyHan(KyHanDTO kyHanDto) {
        if(kyHanRepository.findByMonth(kyHanDto.month()).isPresent()){
            throw new DataNotValidException(400,
                    "Đã tồn tại kỳ hạn có " + kyHanDto.month() + " tháng");
        }
        if(kyHanRepository.findByName(kyHanDto.name()).isPresent()){
            throw new DataNotValidException(400,
                    "Đã tồn tại kỳ hạn có tên: " + kyHanDto.name());
        }
        int type = this.generateNewType();
        KyHan newKyhan = KyHanConverter.convertDTOtoEntity(kyHanDto, type);
        System.out.println(newKyhan);
        kyHanRepository.save(newKyhan);
    }

    private int generateNewType() {
        int type = 1;
        while(kyHanRepository.findByType(type).isPresent()){
            type++;
        }
        return type;
    }

    public void deleteByType(int type){
        kyHanRepository.deleteByType(type);
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
        kyhan.setMinDeposit(kyHanUpdateDto.min_deposit());
//      if(type == 0){
//           kyhan.setMinDateSent(kyHanUpdateDto.minDay());
//      }
        kyhan.setLaisuat(kyHanUpdateDto.laisuat());
        kyHanRepository.save(kyhan);
        System.out.println(kyhan);
    }
}
