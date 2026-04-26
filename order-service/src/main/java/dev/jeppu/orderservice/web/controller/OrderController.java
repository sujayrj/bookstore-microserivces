package dev.jeppu.orderservice.web.controller;

import dev.jeppu.orderservice.config.ApplicationProperties;
import dev.jeppu.orderservice.domain.OrderService;
import dev.jeppu.orderservice.domain.SecurityService;
import dev.jeppu.orderservice.domain.model.CreateOrderRequest;
import dev.jeppu.orderservice.domain.model.CreateOrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final ApplicationProperties properties;
    private final OrderService orderService;
    private final SecurityService securityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        String userName = securityService.getLoginUserName();
        log.debug(
                "createOrder() : Input request received : Customer: {} | DeliveryAddress : {} | Items : {}",
                request.customer(),
                request.deliveryAddress(),
                request.items());
        log.info("Creating order for user : {}", userName);
        CreateOrderResponse orderResponse = orderService.createOrder(userName, request);
        return orderResponse;
    }
}
