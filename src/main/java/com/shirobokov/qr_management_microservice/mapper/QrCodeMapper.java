package com.shirobokov.qr_management_microservice.mapper;

import com.shirobokov.qr_management_microservice.dto.QrCodeMessage;
import com.shirobokov.qr_management_microservice.entity.QrCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QrCodeMapper {
    @Mapping(source="redirectUrl", target = "targetUrl")
    QrCode toQrCode (QrCodeMessage qrCodeMessage);

    @Mapping(source="targetUrl", target="redirectUrl")
    QrCodeMessage toQrCodeMessage (QrCode qrCode);
}
