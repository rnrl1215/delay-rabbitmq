package com.example.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayRabbitMQConfigurationImpl implements MessageQueueConfiguration{

    @Value("${rabbitmq.delay.host}")
    private String host;

    @Value("${rabbitmq.delay.port}")
    private Integer port;

    @Value("${rabbitmq.delay.username}")
    private String username;

    @Value("${rabbitmq.delay.password}")
    private String password;

    @Value("${rabbitmq.delay.queue-name}")
    private String queueName;

    @Value("${rabbitmq.delay.exchange-name}")
    private String exchangeName;


    @Bean
    @Override
    public Queue queue() {
        return new Queue(queueName);
    }


    @Bean
    @Override
    public CustomExchange customExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delay-type", "direct");
        return new CustomExchange(exchangeName, "x-delayed-message", true, false, args);
    }

    @Bean
    @Override
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setPort(port);
        return factory;
    }

    @Bean
    @Override
    public Binding binding(Queue queue, CustomExchange customExchange) {
        return BindingBuilder.bind(queue).to(customExchange).with("*").noargs();
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean(name = "rabbitMQTemplate")
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
