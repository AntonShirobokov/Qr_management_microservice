package com.shirobokov.qr_management_microservice.producer;


import com.shirobokov.qr_management_microservice.entity.QrCode;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Producer {

    static final String EXCHANGE_NAME = "redirect_exchange";
    static final String QUEUE_NAME = "redirect_queue";
    public static final String ROUTING_KEY = "qr.redirect";

    private final AmqpAdmin amqpAdmin;

    private final RabbitTemplate rabbitTemplate;

    public void send(QrCode qrCode) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, "gfjgf");
    }

    public void send(String message) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, message);
    }

}
