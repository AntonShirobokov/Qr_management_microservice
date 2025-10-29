package com.shirobokov.qr_management_microservice.mapper;

import com.shirobokov.qr_management_microservice.dto.*;
import com.shirobokov.qr_management_microservice.entity.QrCode;
import com.shirobokov.qr_management_microservice.entity.QrCodeData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QrCodeMapper {
    @Mapping(source="redirectUrl", target = "targetUrl")
    QrCode toQrCode (QrCodeMessage qrCodeMessage);

    @Mapping(source="targetUrl", target="redirectUrl")
    QrCodeMessage toQrCodeMessage (QrCode qrCode);


    QrCode toQrCodeFromQrCodeSaveRequest (QrCodeSaveRequest qrCodeSaveRequest);

    QrCodeData toQrCodeDataFromQrCodeSaveRequest (QrCodeSaveRequest qrCodeSaveRequest);

    QrCodeDataDTO toQrCodeDataDTOFromQrCodeData(QrCodeData qrCodeData);

    QrCodeDTO toQrCodeDTOFromQrCode(QrCode qrCode);

    @Mapping(source = "qrCodeData.content", target = "content")
    QrCodeListDTO toQrCodeListDTOFromQrCode(QrCode qrCode);
}
