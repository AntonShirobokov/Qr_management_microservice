package com.shirobokov.qr_management_microservice.service;

import com.shirobokov.qr_management_microservice.entity.QrCode;
import com.shirobokov.qr_management_microservice.exception.QrCodeAlreadyExistsException;
import com.shirobokov.qr_management_microservice.repository.QrCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QrCodeService {

    private final QrCodeRepository qrCodeRepository;

    public void save(QrCode qrCode) {
        if (qrCodeRepository.existsById(qrCode.getQrCodeId())) {
            throw new QrCodeAlreadyExistsException("Qr код уже сохранен");
        }
        qrCode.setCreatedAt(LocalDateTime.now());
        qrCodeRepository.save(qrCode);
    }

    public List<QrCode> findAllQrCodesByUserId(UUID userId) {
        return qrCodeRepository.findAllByUserId(userId);
    }

    public boolean deleteQrCode(UUID qrCodeId) {
        Optional<QrCode> qrCode = qrCodeRepository.findById(qrCodeId);
        if (qrCode.isPresent()) {
            qrCodeRepository.delete(qrCode.get());
            return true;
        }
        return false;
    }
}
