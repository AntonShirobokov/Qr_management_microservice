package com.shirobokov.qr_management_microservice.service;

import com.shirobokov.qr_management_microservice.dto.QrCodeStatisticsUpdateDTO;
import com.shirobokov.qr_management_microservice.entity.QrCode;
import com.shirobokov.qr_management_microservice.exception.QrCodeAlreadyExistsException;
import com.shirobokov.qr_management_microservice.exception.QrCodeNotFoundException;
import com.shirobokov.qr_management_microservice.repository.QrCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
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

    public boolean checkQrCodeOwner(UUID qrCodeId, UUID userId) {
        Optional<QrCode> qrCode = qrCodeRepository.findById(qrCodeId);

        if (qrCode.isEmpty()){
            throw new QrCodeNotFoundException("Qr кода с таким id не существует");
        }

        return qrCode.get().getUserId().equals(userId);
    }

    public void updateQrWithStatistics(QrCodeStatisticsUpdateDTO qrCodeStatisticsUpdateDTO) {

        Optional<QrCode> qrCodeOptional = qrCodeRepository.findById(qrCodeStatisticsUpdateDTO.getQrCodeId());

        if (qrCodeOptional.isEmpty()){
            throw new QrCodeNotFoundException("Qr кода с таким id не существует");
        }

        QrCode qrCode = qrCodeOptional.get();

        qrCode.setTitle(qrCodeStatisticsUpdateDTO.getTitle());
        qrCode.setTargetUrl(qrCodeStatisticsUpdateDTO.getTargetUrl());
        qrCodeRepository.save(qrCode);

        log.info("{} Qr код со статистикой обновлен его новые данные: {}", this.getClass().getName(), qrCode);
    }

    public QrCode findQrCodeByQrCodeId(UUID qrCodeId) {
        Optional<QrCode> qrCodeOptional = qrCodeRepository.findById(qrCodeId);
        if (qrCodeOptional.isEmpty()) {
            throw new QrCodeNotFoundException("Qr кода с таким id не существует");
        }
        return qrCodeOptional.get();
    }
}
