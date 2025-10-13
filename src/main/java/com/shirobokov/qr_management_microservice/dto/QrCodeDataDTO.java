package com.shirobokov.qr_management_microservice.dto;

import com.shirobokov.qr_management_microservice.entity.Item;
import lombok.Data;

import java.util.List;

@Data
public class QrCodeDataDTO {

    private List<Item> content;

}
