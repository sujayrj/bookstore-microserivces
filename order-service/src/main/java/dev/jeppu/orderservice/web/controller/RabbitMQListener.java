package dev.jeppu.orderservice.web.controller;

import dev.jeppu.orderservice.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {
    private final ApplicationProperties properties;

    // @RabbitListener(queues = {"${orders.new-orders-queue}"})
    public void readFromNewOrdersQueue(MyPayload message) {
        log.info("Msg : {} read from : {}", message.content(), properties.newOrdersQueue());
    }

    // @RabbitListener(queues = "${orders.delivered-orders-queue}")
    public void readFromDeliveredOrdersQueue(MyPayload payload) {
        log.info("Msg : {} read from : {}", payload.content(), properties.deliveredOrdersQueue());
    }
}
