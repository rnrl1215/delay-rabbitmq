package com.example.rabbitmqconsumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class RabbitmqConsumerApplication {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setPassword("guest");
        factory.setUsername("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueBind("message","delayed.message","*");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {

            // 처리가 제대로 안될때 ack 를 보내지 않는다.
            try {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                Thread.sleep(1000000L);
                System.out.println(" [x] Received '" + message + "'");
            } catch (Exception e) {
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume("message",true, deliverCallback, consumerTag -> { });

    }

}
