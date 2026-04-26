package dev.jeppu.orderservice.web.controller;

import dev.jeppu.orderservice.AbstractITest;
import dev.jeppu.orderservice.domain.SecurityService;
import dev.jeppu.orderservice.domain.model.Address;
import dev.jeppu.orderservice.domain.model.CreateOrderRequest;
import dev.jeppu.orderservice.domain.model.Customer;
import dev.jeppu.orderservice.domain.model.OrderItem;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import java.util.Set;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public class OrderControllerIT extends AbstractITest {
    @MockitoBean
    private SecurityService securityService;

    @Test
    void shouldCreateProductSuccessfully() {
        mockUser();
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(validRequest())
                .when()
                .post("/api/orders")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body(Matchers.notNullValue());
    }

    @Test
    void shouldFailWhenItemsAreEmpty() {
        mockUser();
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(invalidRequestWithItemsEmpty())
                .when()
                .post("/api/orders")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errors", Matchers.containsString("Items cannot be empty"));
    }

    @Test
    void shouldFailWhenCityIsBlank() {
        mockUser();
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(invalidRequestWithCityBlank())
                .when()
                .post("/api/orders")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errors", Matchers.containsString("Address City cannot be blank"));
    }

    CreateOrderRequest invalidRequestWithCityBlank() {
        return new CreateOrderRequest(
                new Customer("John Doe", "john.doe@gmail.com", "99898989898"),
                new Address("L1", "L2", "", "State", "Country", "1111111"),
                Set.of(new OrderItem("P001", "Product1", 100, BigDecimal.valueOf(104.5))));
    }

    CreateOrderRequest invalidRequestWithItemsEmpty() {
        return new CreateOrderRequest(
                new Customer("John Doe", "john.doe@gmail.com", "99898989898"),
                new Address("L1", "L2", "City", "State", "Country", "1111111"),
                null);
    }

    CreateOrderRequest validRequest() {
        return new CreateOrderRequest(
                new Customer("John Doe", "john.doe@gmail.com", "9876544489"),
                new Address("Line1", "Line2", "Bangalore", "Karnataka", "India", "560050"),
                Set.of(new OrderItem("P001", "Product1", 100, BigDecimal.valueOf(104.5))));
    }

    void mockUser() {
        Mockito.when(securityService.getLoginUserName()).thenReturn("test-user");
    }
}
