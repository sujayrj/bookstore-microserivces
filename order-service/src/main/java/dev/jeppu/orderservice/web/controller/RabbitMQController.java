package dev.jeppu.orderservice.web.controller;

import dev.jeppu.orderservice.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RabbitMQController {
    private final ApplicationProperties properties;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping("/send")
    public String sendMessage(@RequestBody MyMessage message) {
        rabbitTemplate.convertAndSend(properties.orderEventExchange(), message.routingKey(), message.payload());
        log.info("Message : {} ,  published to : {}", message.payload().content(), message.routingKey());
        return "Message : " + message.payload().content() + " published to : " + message.routingKey();
    }
}

record MyMessage(String routingKey, MyPayload payload) {}

record MyPayload(String content) {}
