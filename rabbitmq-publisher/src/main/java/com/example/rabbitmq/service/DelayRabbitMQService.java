package com.example.rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DelayRabbitMQService {

    private final AmqpTemplate rabbitTemplate;

    public DelayRabbitMQService(@Qualifier("rabbitMQTemplate") AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.delay.routing-key}")
    private String routingKey;

    @Value("${rabbitmq.delay.exchange-name}")
    private String exchangeName;

    public void publishMessageWithDelayTime(String message, Long delayTime) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message, m->{
            m.getMessageProperties().setHeader("x-delay", delayTime);
            return m;
        });
    }
}
