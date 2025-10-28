package com.shirobokov.qr_management_microservice.restcontroller;

import com.shirobokov.qr_management_microservice.dto.QrCodeDTO;
import com.shirobokov.qr_management_microservice.dto.QrCodeSaveRequest;
import com.shirobokov.qr_management_microservice.dto.QrCodeListUpdateDTO;
import com.shirobokov.qr_management_microservice.dto.QrCodeStatisticsUpdateDTO;
import com.shirobokov.qr_management_microservice.entity.QrCode;
import com.shirobokov.qr_management_microservice.entity.QrCodeData;
import com.shirobokov.qr_management_microservice.entity.enums.QrType;
import com.shirobokov.qr_management_microservice.mapper.QrCodeMapper;
import com.shirobokov.qr_management_microservice.rabbit.producer.QrCodeProducer;
import com.shirobokov.qr_management_microservice.service.QrCodeDataService;
import com.shirobokov.qr_management_microservice.service.QrCodeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class QrController {

    private final QrCodeMapper qrCodeMapper;

    private final QrCodeService qrCodeService;

    private final QrCodeDataService qrCodeDataService;

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
    public ResponseEntity<?> getAllQrCodes(HttpServletRequest httpServletRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = (UUID) authentication.getPrincipal();
        log.info("Полученный uuid: {}", userId.toString());


        List<QrCode> qrList = qrCodeService.findAllQrCodesByUserId(userId);

        log.info("Полученные значения qrCode для пользователя из БД: {}", qrList);

        List<QrCodeDTO> qrListTest = qrList.stream().map(qrCodeMapper::toQrCodeDTOFromQrCode).toList();

        return ResponseEntity.ok(qrListTest);
    }

    @DeleteMapping("/deleteQrCode")
    public ResponseEntity<?> deleteQrCode(@RequestBody Map<String, String> payload) {
        UUID qrCodeId = UUID.fromString(payload.get("qrCodeId"));
        QrType type = QrType.valueOf(payload.get("type"));

        log.info("Получен id qr кода: {}, с типом: {}", qrCodeId, type);

        if (qrCodeService.deleteQrCode(qrCodeId)) {
            if (!type.equals(QrType.simpleQr)) {
                qrCodeProducer.delete(qrCodeId);
            }
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Qr код не найден"));
    }

    @PutMapping("/updateQrList")
    public ResponseEntity<?> updateQrList(@RequestBody QrCodeListUpdateDTO qrCodeListUpdateDTO) {

        log.info("{} Новые данные qrList: {}", this.getClass().getName(), qrCodeListUpdateDTO);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = (UUID) authentication.getPrincipal();

        if (qrCodeService.checkQrCodeOwner(qrCodeListUpdateDTO.getQrCodeId(), userId)) {
            qrCodeDataService.updateQrCode(qrCodeListUpdateDTO);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Вы не можете редактировать этот qr код"));
    }

    @PutMapping("/updateQrWithStatistics")
    public ResponseEntity<?> updateQrWithStatistics(@RequestBody QrCodeStatisticsUpdateDTO qrCodeStatisticsUpdateDTO) {
        log.info("{} Новые данные qrWithStatistics: {}", this.getClass().getName(), qrCodeStatisticsUpdateDTO);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = (UUID) authentication.getPrincipal();

        if (qrCodeService.checkQrCodeOwner(qrCodeStatisticsUpdateDTO.getQrCodeId(), userId)) {
            qrCodeService.updateQrWithStatistics(qrCodeStatisticsUpdateDTO);
            qrCodeProducer.update(qrCodeStatisticsUpdateDTO.getQrCodeId(), qrCodeStatisticsUpdateDTO.getTargetUrl());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Вы не можете редактировать этот qr код"));
    }

}
