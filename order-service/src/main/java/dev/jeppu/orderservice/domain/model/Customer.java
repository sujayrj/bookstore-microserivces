package dev.jeppu.orderservice.domain.model;

import jakarta.validation.constraints.NotBlank;

public record Customer(
        @NotBlank(message = "Customer name cannot be blank") String name,
        @NotBlank(message = "Customer email cannot be blank") String email,
        @NotBlank(message = "Customer phone cannot be blank") String phone) {}
