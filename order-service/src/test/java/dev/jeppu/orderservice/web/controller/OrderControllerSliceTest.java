package dev.jeppu.orderservice.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.jeppu.orderservice.config.ApplicationProperties;
import dev.jeppu.orderservice.domain.OrderService;
import dev.jeppu.orderservice.domain.SecurityService;
import dev.jeppu.orderservice.domain.model.CreateOrderResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
class OrderControllerSliceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private SecurityService securityService;

    @MockitoBean
    private ApplicationProperties applicationProperties;

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {

        Mockito.when(securityService.getLoginUserName()).thenReturn("test-user");

        Mockito.when(orderService.createOrder(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new CreateOrderResponse("ORDER-123"));

        String requestJson =
                """
        {
          "customer": {
            "name": "John Doe",
            "email": "john.doe@gmail.com",
            "phone": "99898989898"
          },
          "deliveryAddress": {
            "line1": "L1",
            "line2": "L2",
            "city": "Bangalore",
            "state": "State",
            "country": "Country",
            "zipCode": "111111"
          },
          "items": [
            {
              "code": "P001",
              "name": "Product1",
              "quantity": 100,
              "price": 104.5
            }
          ]
        }
        """;

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(Matchers.containsString("ORDER-123")));
    }

    @Test
    void shouldFailWhenCustomerNameMissing() throws Exception {

        Mockito.when(securityService.getLoginUserName()).thenReturn("test-user");

        String requestJson =
                """
    {
      "customer": {
        "name": "",
        "email": "john.doe@gmail.com",
        "phone": "99898989898"
      },
      "deliveryAddress": {
        "line1": "L1",
        "line2": "L2",
        "city": "Bangalore",
        "state": "State",
        "country": "Country",
        "zipCode": "111111"
      },
      "items": [
        {
          "code": "P001",
          "name": "Product1",
          "quantity": 100,
          "price": 104.5
        }
      ]
    }
    """;

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(Matchers.containsString("Customer name cannot be blank")));
    }

    @Test
    void shouldFailWhenPriceInvalid() throws Exception {

        Mockito.when(securityService.getLoginUserName()).thenReturn("test-user");

        String requestJson =
                """
    {
      "customer": {
        "name": "John Doe",
        "email": "john.doe@gmail.com",
        "phone": "99898989898"
      },
      "deliveryAddress": {
        "line1": "L1",
        "line2": "L2",
        "city": "Bangalore",
        "state": "State",
        "country": "Country",
        "zipCode": "111111"
      },
      "items": [
        {
          "code": "P001",
          "name": "Product1",
          "quantity": 100,
          "price": 0
        }
      ]
    }
    """;

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(Matchers.containsString("Price must be minimum 1")));
    }

    @Test
    void shouldFailWhenItemsMissing() throws Exception {

        Mockito.when(securityService.getLoginUserName()).thenReturn("test-user");

        String requestJson =
                """
    {
      "customer": {
        "name": "John Doe",
        "email": "john.doe@gmail.com",
        "phone": "99898989898"
      },
      "deliveryAddress": {
        "line1": "L1",
        "line2": "L2",
        "city": "Bangalore",
        "state": "State",
        "country": "Country",
        "zipCode": "111111"
      },
      "items": []
    }
    """;

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(Matchers.containsString("Items cannot be empty")));
    }

    @Test
    void shouldReturnMultipleValidationErrors() throws Exception {

        Mockito.when(securityService.getLoginUserName()).thenReturn("test-user");

        String requestJson =
                """
    {
      "customer": {
        "name": "",
        "email": "",
        "phone": ""
      },
      "deliveryAddress": {
        "line1": null,
        "line2": null,
        "city": "",
        "state": "",
        "country": "",
        "zipCode": ""
      },
      "items": []
    }
    """;

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(
                        "$.errors",
                        Matchers.allOf(
                                Matchers.containsString("Customer name cannot be blank"),
                                Matchers.containsString("Customer email cannot be blank"),
                                Matchers.containsString("Items cannot be empty"))));
    }
}
