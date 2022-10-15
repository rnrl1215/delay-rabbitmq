package com.example.rabbitmq.api;

import com.example.rabbitmq.dto.MessageRequest;
import com.example.rabbitmq.service.DelayRabbitMQService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MessageQueueAPI {
    private final DelayRabbitMQService delayRabbitMQService;

    @PostMapping("/publish")
    public ResponseEntity publishMessage(@RequestBody MessageRequest messageRequest) {
        delayRabbitMQService.publishMessageWithDelayTime(messageRequest.getMessage(),1L);

        return new ResponseEntity("Publish your message", HttpStatus.OK);
    }
}
