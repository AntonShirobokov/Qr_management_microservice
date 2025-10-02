package com.shirobokov.qr_management_microservice.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;


@Entity
@Table(name="qr_code_data")
@Data
public class QrCodeData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "qr_code_data_id")
    private int qrCodeDataId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name="content")
    private List<Item> content;

    @OneToOne
    @JoinColumn(name="qr_code_id", referencedColumnName = "qr_code_id")
    private QrCode qrCode;
}
