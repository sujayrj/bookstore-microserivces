package dev.jeppu.catalogservice.web;

import dev.jeppu.catalogservice.AbstractITest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test-data.sql")
class ProductControllerTest extends AbstractITest {

    @Test
    void testGetAllProducts() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/products?page=1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("totalPages", Matchers.equalTo(2))
                .body("totalElements", Matchers.equalTo(16))
                .body("result", Matchers.hasSize(10))
                .body("result[0].code", Matchers.is("P100"));
    }

    @Test
    void testGetProductByCode() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/products/{code}", "P100")
                .then()
                .body("code", Matchers.is("P100"))
                .body("name", Matchers.is("The Hunger Games"))
                .body("description", Matchers.is("Winning will make you famous. Losing means certain death..."))
                .body("imageUrl", Matchers.is("https://images.gr-assets.com/books/1447303603l/2767052.jpg"))
                .body("price", Matchers.is(34.0F));
    }
}
