package com.shirobokov.qr_management_microservice.util;

import org.springframework.core.io.ClassPathResource;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtils {

    public static PublicKey loadPublicKey(String publicKeyPath) throws Exception {
        String key = new String(new ClassPathResource(publicKeyPath).getInputStream().readAllBytes())
                .replaceAll("\\r?\\n", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "");
        byte[] keyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

}
