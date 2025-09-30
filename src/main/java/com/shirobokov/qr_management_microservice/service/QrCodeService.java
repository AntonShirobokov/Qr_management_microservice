package com.shirobokov.qr_management_microservice.service;

import com.shirobokov.qr_management_microservice.entity.QrCode;
import com.shirobokov.qr_management_microservice.repository.QrCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QrCodeService {

    private final QrCodeRepository qrCodeRepository;

    public void save(QrCode qrCode) {
        qrCode.setCreatedAt(LocalDateTime.now());
        qrCodeRepository.save(qrCode);
    }

}
