package com.example.rabbitmq.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DelayRabbitMQService {

    private final AmqpTemplate rabbitTemplate;

    public DelayRabbitMQService(@Qualifier("rabbitMQTemplate") AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.delay.queue-name}")
    private String queueName;

    @Value("${rabbitmq.delay.exchange-name}")
    private String exchangeName;

    public void publishMessageWithDelayTime(String message, Long delayTime) {
        rabbitTemplate.convertAndSend(exchangeName, queueName, message, m->{
            m.getMessageProperties().setHeader("x-delay", delayTime);
            return m;
        });
    }
}
