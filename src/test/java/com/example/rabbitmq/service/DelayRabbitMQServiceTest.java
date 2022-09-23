package com.example.rabbitmq.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DelayRabbitMQServiceTest {
    @Autowired
    private DelayRabbitMQService delayRabbitMQService;

    @Test
    public void sendMessage () {
        delayRabbitMQService.publishMessageWithDelayTime("test", 1L);
    }
}