package com.shirobokov.qr_management_microservice.entity;


import com.shirobokov.qr_management_microservice.entity.enums.QrType;
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
    @Column(name="qr_code_id")
    private UUID qrCodeId;

    @Column(name="user_id")
    private UUID userId;

    @Column(name="title")
    private String title;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name="type")
    private QrType type;

    @Column(name="qr_url")
    private String qrUrl;

    @Column(name="target_url")
    private String targetUrl;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "qrCode", cascade = CascadeType.ALL)
    private QrCodeData qrCodeData;

}
