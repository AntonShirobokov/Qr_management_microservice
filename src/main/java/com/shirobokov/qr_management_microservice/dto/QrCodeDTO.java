package com.shirobokov.qr_management_microservice.dto;


import com.shirobokov.qr_management_microservice.entity.QrCodeData;
import com.shirobokov.qr_management_microservice.entity.enums.QrType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class QrCodeDTO {

    private UUID qrCodeId;

    private String title;

    private QrType type;

    private String qrUrl;

    private String targetUrl;

    private LocalDateTime createdAt;

    private QrCodeDataDTO qrCodeData;

}
