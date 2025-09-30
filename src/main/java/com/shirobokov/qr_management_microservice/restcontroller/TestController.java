package com.shirobokov.qr_management_microservice.restcontroller;


import com.shirobokov.qr_management_microservice.entity.QrCode;
import com.shirobokov.qr_management_microservice.rabbit.producer.QrCodeProducer;
import com.shirobokov.qr_management_microservice.service.QrCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final QrCodeProducer qrCodeProducer;

    private final QrCodeService qrCodeService;


    @GetMapping("/public")
    public ResponseEntity<?> publicEndpoint(@RequestParam(name="message") String message){
        return ResponseEntity.ok("Public endpoint");
    }

    @GetMapping("/private")
    public ResponseEntity<?> privateEndpoint(){
        return ResponseEntity.ok("Private endpoint");
    }

    @PostMapping("/test")
    public ResponseEntity<?> testEndpoint(@RequestBody QrCode qrCode) {

//        SecurityContext contextHolder = SecurityContextHolder.getContext();
//        log.info("Credentials: {}",  contextHolder.getAuthentication().getCredentials());
//        log.info("Principal: {}", contextHolder.getAuthentication().getPrincipal());


        log.info(qrCode.toString());

        qrCodeService.save(qrCode);

        qrCodeProducer.send(qrCode);

        return ResponseEntity.ok().build();
    }

}
