package com.shirobokov.qr_management_microservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class QrCodeStatisticsUpdateDTO {
    private UUID qrCodeId;
    private String title;
    private String targetUrl;
}
