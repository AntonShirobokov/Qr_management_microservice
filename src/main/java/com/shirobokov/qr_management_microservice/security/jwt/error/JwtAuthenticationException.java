package com.shirobokov.qr_management_microservice.security.jwt.error;


public class JwtAuthenticationException extends RuntimeException{

    public JwtAuthenticationException (String message) {
        super(message);
    }

    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
