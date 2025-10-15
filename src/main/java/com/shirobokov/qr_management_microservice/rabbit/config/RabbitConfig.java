package com.shirobokov.qr_management_microservice.rabbit.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    public static final String FANOUT_EXCHANGE_NAME_REDIRECT = "redirect_exchange";
    public static final String FANOUT_EXCHANGE_NAME_DELETE = "delete_exchange";
    public static final String REDIRECT_QUEUE_NAME = "redirect_queue";
    public static final String DELETE_QUEUE_NAME = "delete_queue";

    @Bean()
    public Queue redirectQueue() {
        return new Queue(REDIRECT_QUEUE_NAME, true);
    }
    @Bean()
    public Queue deleteQueue() {
        return new Queue(DELETE_QUEUE_NAME, true);
    }

    @Bean()
    public FanoutExchange fanoutExchangeRedirect() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME_REDIRECT);
    }

    @Bean()
    public FanoutExchange fanoutExchangeDelete() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME_DELETE);
    }

    @Bean
    public Binding bindingRedirect(Queue redirectQueue, FanoutExchange fanoutExchangeRedirect) {
        return BindingBuilder.bind(redirectQueue).to(fanoutExchangeRedirect);
    }

    @Bean
    public Binding bindingDelete(Queue deleteQueue, FanoutExchange fanoutExchangeDelete) {
        return BindingBuilder.bind(deleteQueue).to(fanoutExchangeDelete);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory, Queue redirectQueue, Queue deleteQueue,
                                   FanoutExchange fanoutExchangeRedirect, FanoutExchange fanoutExchangeDelete,
                                   Binding bindingRedirect, Binding bindingDelete) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);

        admin.declareQueue(redirectQueue);
        admin.declareQueue(deleteQueue);
        admin.declareExchange(fanoutExchangeRedirect);
        admin.declareExchange(fanoutExchangeDelete);
        admin.declareBinding(bindingRedirect);
        admin.declareBinding(bindingDelete);

        return admin;
    }
}
