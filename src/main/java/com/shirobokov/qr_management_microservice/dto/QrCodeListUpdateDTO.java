package com.shirobokov.qr_management_microservice.dto;

import com.shirobokov.qr_management_microservice.entity.Item;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class QrCodeListUpdateDTO {

    private UUID qrCodeId;

    private String title;

    private List<Item> content;

}
