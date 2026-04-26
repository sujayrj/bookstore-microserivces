package dev.jeppu.orderservice.domain.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OrderItem(
        @NotBlank(message = "Code is required") String code,
        @NotBlank(message = "Name is required") String name,
        @NotNull(message = "Quantity is required") Integer quantity,
        @NotNull(message = "Price is required") @Min(value = 1, message = "Price must be minimum 1") BigDecimal price) {}
