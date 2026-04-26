package dev.jeppu.orderservice.domain;

import dev.jeppu.orderservice.domain.model.CreateOrderRequest;
import dev.jeppu.orderservice.domain.model.CreateOrderResponse;
import dev.jeppu.orderservice.domain.model.OrderEntity;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public CreateOrderResponse createOrder(String userName, CreateOrderRequest request) {
        OrderEntity orderEntity = OrderMapper.convertToEntity(request);
        orderEntity.setUsername(userName);
        orderEntity.setCreatedAt(LocalDateTime.now());
        orderRepository.save(orderEntity);
        log.info(
                "Order with id : {} and orderRef : {} saved successfully",
                orderEntity.getId(),
                orderEntity.getOrderRef());
        return new CreateOrderResponse(String.valueOf(orderEntity.getOrderRef()));
    }
}
