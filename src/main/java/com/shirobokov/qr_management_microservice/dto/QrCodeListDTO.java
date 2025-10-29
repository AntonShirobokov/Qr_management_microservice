package com.shirobokov.qr_management_microservice.dto;

import com.shirobokov.qr_management_microservice.entity.Item;
import com.shirobokov.qr_management_microservice.entity.QrCodeData;
import com.shirobokov.qr_management_microservice.entity.enums.QrType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class QrCodeListDTO {
    private UUID qrCodeId;
    private String title;
    private QrType type;
    private List<Item> content;
}
