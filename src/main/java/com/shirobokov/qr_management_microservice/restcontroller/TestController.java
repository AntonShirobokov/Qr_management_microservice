package com.shirobokov.qr_management_microservice.restcontroller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @PostMapping("/test")
    public ResponseEntity<?> test(HttpServletRequest httpServletRequest) {
        String xForward = httpServletRequest.getHeader("X-Forwarded-For");
        if (xForward==null) {
            return ResponseEntity.badRequest().body("Заголовка X-Forwarded-For не найдено");
        }
        return ResponseEntity.ok(xForward);
    }
}
