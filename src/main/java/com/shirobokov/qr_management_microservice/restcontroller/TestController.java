package com.shirobokov.qr_management_microservice.restcontroller;


import com.shirobokov.qr_management_microservice.entity.QrCode;
import com.shirobokov.qr_management_microservice.producer.Producer;
import com.shirobokov.qr_management_microservice.service.QrCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final Producer producer;

    private final QrCodeService qrCodeService;


    @GetMapping("/public")
    public ResponseEntity<?> publicEndpoint(@RequestParam(name="message") String message){
        producer.send(message);
        return ResponseEntity.ok("Public endpoint");
    }

    @GetMapping("/private")
    public ResponseEntity<?> privateEndpoint(){
        return ResponseEntity.ok("Private endpoint");
    }

    @PostMapping("/test")
    public ResponseEntity<?> testEndpoint(@RequestBody QrCode qrCode) {

        log.info(qrCode.toString());

        qrCodeService.save(qrCode);

        producer.send(qrCode);

        return ResponseEntity.ok().build();
    }

}
