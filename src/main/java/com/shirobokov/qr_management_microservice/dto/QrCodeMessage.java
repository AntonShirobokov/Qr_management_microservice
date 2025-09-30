package com.shirobokov.qr_management_microservice.dto;

import com.shirobokov.qr_management_microservice.entity.enums.QrType;
import lombok.Data;

import java.util.UUID;

@Data
public class QrCodeMessage {

    private UUID qrCodeId;

    private QrType type;

    private String redirectUrl;
}
