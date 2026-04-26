package dev.jeppu.orderservice.domain;

import dev.jeppu.orderservice.domain.model.*;
import java.util.List;
import java.util.UUID;

public class OrderMapper {
    static OrderEntity convertToEntity(CreateOrderRequest createOrderRequest) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderRef(UUID.randomUUID().toString());
        orderEntity.setStatus(OrderStatus.NEW);
        orderEntity.setCustomer(createOrderRequest.customer());
        orderEntity.setDeliveryAddress(createOrderRequest.deliveryAddress());

        List<OrderItemsEntity> orderItemsEntities = createOrderRequest.items().stream()
                .map(orderItem -> toOrderItemEntity(orderItem, orderEntity))
                .toList();

        orderEntity.setOrderItems(orderItemsEntities);
        return orderEntity;
    }

    static OrderItemsEntity toOrderItemEntity(OrderItem orderItem, OrderEntity orderEntity) {
        OrderItemsEntity orderItemsEntity = new OrderItemsEntity();
        orderItemsEntity.setCode(orderItem.code());
        orderItemsEntity.setName(orderItem.name());
        orderItemsEntity.setPrice(orderItem.price());
        orderItemsEntity.setQuantity(orderItem.quantity());
        orderItemsEntity.setOrder(orderEntity);
        return orderItemsEntity;
    }
}
