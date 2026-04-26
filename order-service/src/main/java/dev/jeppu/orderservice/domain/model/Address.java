package dev.jeppu.orderservice.domain.model;

import jakarta.validation.constraints.NotBlank;

public record Address(
        String line1,
        String line2,
        @NotBlank(message = "Address City cannot be blank") String city,
        @NotBlank(message = "Address State cannot be blank") String state,
        @NotBlank(message = "Address Country cannot be blank") String country,
        @NotBlank(message = "Address Zipcode cannot be blank") String zipCode) {}
