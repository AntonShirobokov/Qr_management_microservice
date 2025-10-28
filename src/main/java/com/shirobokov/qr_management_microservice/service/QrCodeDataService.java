package com.shirobokov.qr_management_microservice.service;

import com.shirobokov.qr_management_microservice.dto.QrCodeListUpdateDTO;
import com.shirobokov.qr_management_microservice.entity.QrCode;
import com.shirobokov.qr_management_microservice.entity.QrCodeData;
import com.shirobokov.qr_management_microservice.exception.QrCodeNotFoundException;
import com.shirobokov.qr_management_microservice.repository.QrCodeDataRepository;
import com.shirobokov.qr_management_microservice.repository.QrCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QrCodeDataService {

    private final QrCodeDataRepository qrCodeDataRepository;

    private final QrCodeRepository qrCodeRepository;

    public void updateQrCode(QrCodeListUpdateDTO qrCodeListUpdateDTO) {
        Optional<QrCode> qrCodeOptional = qrCodeRepository.findById(qrCodeListUpdateDTO.getQrCodeId());

        if (qrCodeOptional.isEmpty()) {
            throw new QrCodeNotFoundException("Qr кода с таким id не существует");
        }
        QrCode qrCode = qrCodeOptional.get();
        QrCodeData qrCodeData = qrCode.getQrCodeData();

        qrCode.setTitle(qrCodeListUpdateDTO.getTitle());
        qrCodeData.setContent(qrCodeListUpdateDTO.getContent());

        qrCodeRepository.save(qrCode);
        qrCodeDataRepository.save(qrCodeData);

        log.info("{} Успешное обновление: {}",this.getClass().getName(), qrCode);
    }

}
