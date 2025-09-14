package com.shirobokov.qr_management_microservice.security.jwt;


import com.shirobokov.qr_management_microservice.security.jwt.error.JwtAuthenticationException;
import com.shirobokov.qr_management_microservice.util.JwtKeyProperties;
import com.shirobokov.qr_management_microservice.util.KeyUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.PublicKey;

@Component
@Slf4j
public class JwtAuthenticationDeserializer {

    private PublicKey publicKey;

    public JwtAuthenticationDeserializer(JwtKeyProperties jwtKeyProperties) throws Exception{
        this.publicKey = KeyUtils.loadPublicKey(jwtKeyProperties.getPublicKeyPath());
    }

    public Claims getClaimsFromToken (String accessToken) {
        try {
            return Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(accessToken).getPayload();
        } catch (SignatureException e) {
            log.warn("Ошибка подписи токена: {}", e.getMessage());
            throw new JwtAuthenticationException("Ошибка с подписью токена", e);
        } catch (ExpiredJwtException e) {
            log.warn("Срок действия токена истёк: {}", e.getMessage());
            throw new JwtAuthenticationException("Срок действия токена истёк", e);
        } catch (JwtException e) {
            log.warn("Ошибка при обработке токена: {}", e.getMessage());
            throw new JwtAuthenticationException("Некорректный токен", e);
        }

    }
}
