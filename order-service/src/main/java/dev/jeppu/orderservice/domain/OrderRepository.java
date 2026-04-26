package dev.jeppu.orderservice.domain;

import dev.jeppu.orderservice.domain.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {}
