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
    public static final String FANOUT_EXCHANGE_NAME_UPDATE = "update_exchange";
    public static final String REDIRECT_QUEUE_NAME = "redirect_queue";
    public static final String DELETE_QUEUE_NAME = "delete_queue";
    public static final String UPDATE_QUEUE_NAME = "update_queue";

    @Bean
    public Queue redirectQueue() {
        return new Queue(REDIRECT_QUEUE_NAME, true);
    }
    @Bean
    public Queue deleteQueue() {
        return new Queue(DELETE_QUEUE_NAME, true);
    }
    @Bean
    public Queue updateQueue() {
        return new Queue(UPDATE_QUEUE_NAME, true);
    }

    @Bean
    public FanoutExchange fanoutExchangeRedirect() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME_REDIRECT);
    }

    @Bean
    public FanoutExchange fanoutExchangeDelete() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME_DELETE);
    }

    @Bean
    public FanoutExchange fanoutExchangeUpdate() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME_UPDATE);
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
    public Binding bindingUpdate(Queue updateQueue, FanoutExchange fanoutExchangeUpdate) {
        return BindingBuilder.bind(updateQueue).to(fanoutExchangeUpdate);
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
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory, Queue redirectQueue, Queue deleteQueue,Queue updateQueue,
                                   FanoutExchange fanoutExchangeRedirect, FanoutExchange fanoutExchangeDelete,FanoutExchange fanoutExchangeUpdate,
                                   Binding bindingRedirect, Binding bindingDelete, Binding bindingUpdate) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);

        admin.declareQueue(redirectQueue);
        admin.declareQueue(deleteQueue);
        admin.declareQueue(updateQueue);
        admin.declareExchange(fanoutExchangeRedirect);
        admin.declareExchange(fanoutExchangeDelete);
        admin.declareExchange(fanoutExchangeUpdate);
        admin.declareBinding(bindingRedirect);
        admin.declareBinding(bindingDelete);
        admin.declareBinding(bindingUpdate);

        return admin;
    }
}
