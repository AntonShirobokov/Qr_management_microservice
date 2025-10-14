package com.shirobokov.qr_management_microservice.repository;


import com.shirobokov.qr_management_microservice.entity.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {


    List<QrCode> findAllByUserId(UUID userId);
}
