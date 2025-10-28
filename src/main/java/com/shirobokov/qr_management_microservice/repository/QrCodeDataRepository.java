package com.shirobokov.qr_management_microservice.repository;

import com.shirobokov.qr_management_microservice.entity.QrCodeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QrCodeDataRepository extends JpaRepository<QrCodeData, Integer> {



}
