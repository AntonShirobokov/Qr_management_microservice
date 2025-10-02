package com.shirobokov.qr_management_microservice.dto;

import com.shirobokov.qr_management_microservice.entity.Item;
import com.shirobokov.qr_management_microservice.entity.enums.QrType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class QrCodeSaveRequest {

    private UUID qrCodeId;

    private UUID userId;

    private String targetUrl;

    private String qrUrl;

    private QrType type;

    private List<Item> content;
}
