package com.shirobokov.qr_management_microservice.restcontroller;

import com.shirobokov.qr_management_microservice.dto.QrCodeSaveRequest;
import com.shirobokov.qr_management_microservice.entity.QrCode;
import com.shirobokov.qr_management_microservice.entity.QrCodeData;
import com.shirobokov.qr_management_microservice.mapper.QrCodeMapper;
import com.shirobokov.qr_management_microservice.service.QrCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class QrController {

    private final QrCodeMapper qrCodeMapper;

    private final QrCodeService qrCodeService;

    @PostMapping("/saveQr")
    public ResponseEntity<?> saveQr(@RequestBody QrCodeSaveRequest qrCodeSaveRequest) {

        log.info("Получен qrCodeSaveRequest: {}", qrCodeSaveRequest);

        QrCode qrCode = qrCodeMapper.toQrCodeFromQrCodeSaveRequest(qrCodeSaveRequest);
        QrCodeData qrCodeData = qrCodeMapper.toQrCodeDataFromQrCodeSaveRequest(qrCodeSaveRequest);

        log.info("Преобразованный qrCode: {}", qrCode);
        log.info("Преобразованный qrCodeData: {}", qrCodeData);

        qrCode.setQrCodeData(qrCodeData);
        qrCodeData.setQrCode(qrCode);
        qrCodeService.save(qrCode);

        return ResponseEntity.ok("QrCode успешно получен на микросервисе Qr_management");
    }
}
