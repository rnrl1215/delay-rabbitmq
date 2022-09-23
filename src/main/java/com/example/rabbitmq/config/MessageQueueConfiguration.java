package com.example.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

public interface MessageQueueConfiguration {
    public Queue queue();
    public CustomExchange customExchange();
    public Binding binding(Queue queue, CustomExchange customExchange);
    public ConnectionFactory connectionFactory();
}
