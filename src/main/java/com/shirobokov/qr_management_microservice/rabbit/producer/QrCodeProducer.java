package com.shirobokov.qr_management_microservice.rabbit.producer;

import com.shirobokov.qr_management_microservice.dto.QrCodeMessage;
import com.shirobokov.qr_management_microservice.entity.QrCode;
import com.shirobokov.qr_management_microservice.mapper.QrCodeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class QrCodeProducer {

    public static final String EXCHANGE_NAME = "redirect_exchange";
    public static final String QUEUE_NAME = "redirect_queue";
    public static final String ROUTING_KEY = "qr.redirect";

    private final QrCodeMapper qrCodeMapper;
    private final AmqpAdmin amqpAdmin;
    private final RabbitTemplate rabbitTemplate;

    public void send(QrCode qrCode) {
        QrCodeMessage qrCodeMessage = qrCodeMapper.toQrCodeMessage(qrCode);

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, qrCodeMessage);

        log.info("Сообщение отправлено в очередь: {}", qrCodeMessage);
    }


}
