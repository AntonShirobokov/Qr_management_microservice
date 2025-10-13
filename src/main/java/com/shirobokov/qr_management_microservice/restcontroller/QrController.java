package com.shirobokov.qr_management_microservice.restcontroller;

import com.shirobokov.qr_management_microservice.dto.QrCodeDTO;
import com.shirobokov.qr_management_microservice.dto.QrCodeDataDTO;
import com.shirobokov.qr_management_microservice.dto.QrCodeSaveRequest;
import com.shirobokov.qr_management_microservice.entity.QrCode;
import com.shirobokov.qr_management_microservice.entity.QrCodeData;
import com.shirobokov.qr_management_microservice.entity.enums.QrType;
import com.shirobokov.qr_management_microservice.mapper.QrCodeMapper;
import com.shirobokov.qr_management_microservice.rabbit.producer.QrCodeProducer;
import com.shirobokov.qr_management_microservice.service.QrCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class QrController {

    private final QrCodeMapper qrCodeMapper;

    private final QrCodeService qrCodeService;

    private final QrCodeProducer qrCodeProducer;

    @GetMapping("/private")
    public ResponseEntity<?> privateEndpoint(){
        return ResponseEntity.ok("Private endpoint");
    }


    @PostMapping("/saveQr")
    public ResponseEntity<?> saveQr(@RequestBody QrCodeSaveRequest qrCodeSaveRequest) {

        log.info("Получен qrCodeSaveRequest: {}", qrCodeSaveRequest);

        QrCode qrCode = qrCodeMapper.toQrCodeFromQrCodeSaveRequest(qrCodeSaveRequest);
        QrCodeData qrCodeData = qrCodeMapper.toQrCodeDataFromQrCodeSaveRequest(qrCodeSaveRequest);

        if (qrCode.getType().equals(QrType.qrList)) {
            qrCode.setQrCodeData(qrCodeData);
            qrCodeData.setQrCode(qrCode);
        }

        qrCodeService.save(qrCode);
        if (!qrCode.getType().equals(QrType.simpleQr)){
            qrCodeProducer.send(qrCode);
        }

        return ResponseEntity.ok("QrCode успешно получен на микросервисе Qr_management");
    }

    @GetMapping("/getAllQrCodes")
    public ResponseEntity<?> getAllQrCodes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID user_id = (UUID) authentication.getPrincipal();
        log.info("Полученный uuid: {}", user_id.toString());


        List<QrCode> qrList = qrCodeService.findAllQrCodesByUserId(user_id);

        log.info("Полученные значения qrCode для пользователя из БД: {}", qrList);

        List<QrCodeDTO> qrListTest = qrList.stream().map(qrCodeMapper::toQrCodeDTOFromQrCode).toList();

        return ResponseEntity.ok(qrListTest);
    }
}
