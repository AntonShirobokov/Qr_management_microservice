package com.shirobokov.qr_management_microservice.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="qr_codes")
@Data
@ToString
public class QrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="qrcode_id")
    private UUID qrCodeId;

    @Column(name="user_id")
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name="type")
    private QrType type;

    @Column(name="target_url")
    private String targetUrl;

    @Column(name="created_at")
    private LocalDateTime createdAt;


    public enum QrType {
        simpleQr,
        qrWithStatistics,
        qrList
    }

}
