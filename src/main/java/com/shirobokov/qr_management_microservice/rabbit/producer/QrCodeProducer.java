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

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class QrCodeProducer {

    public static final String FANOUT_EXCHANGE_NAME_REDIRECT = "redirect_exchange";
    public static final String FANOUT_EXCHANGE_NAME_DELETE = "delete_exchange";
    public static final String FANOUT_EXCHANGE_NAME_UPDATE = "update_exchange";


    private final QrCodeMapper qrCodeMapper;
    private final RabbitTemplate rabbitTemplate;

    public void send(QrCode qrCode) {
        QrCodeMessage qrCodeMessage = qrCodeMapper.toQrCodeMessage(qrCode);

        rabbitTemplate.convertAndSend(FANOUT_EXCHANGE_NAME_REDIRECT, "", qrCodeMessage);

        log.info("Сообщение отправлено в очередь: {}", qrCodeMessage);
    }

    public void delete(UUID qrCodeId) {
        rabbitTemplate.convertAndSend(FANOUT_EXCHANGE_NAME_DELETE,"", Map.of("qrCodeId", qrCodeId));

        log.info("Qr код отправлен в очередь для удаления: {}", qrCodeId);
    }

    public void update(UUID qrCodeId, String targetUrl) {
        rabbitTemplate.convertAndSend(FANOUT_EXCHANGE_NAME_UPDATE, "", Map.of("qrCodeId", qrCodeId, "targetUrl", targetUrl ));
        log.info("Новые данные отправлен в очередь для обновления в redirect microservice: {}, {}", qrCodeId, targetUrl);
    }

}
