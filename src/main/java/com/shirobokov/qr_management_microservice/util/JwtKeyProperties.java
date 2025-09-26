package com.shirobokov.qr_management_microservice.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtKeyProperties {

    @Value("${jwt.keys.public.path}")
    private String publicKeyPath;

}
