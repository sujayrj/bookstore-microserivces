package dev.jeppu.catalogservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test-data.sql")
@DataJpaTest(
        properties = {"spring.test.database.replace=none", "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db"})
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void getProduct() {
        Optional<ProductEntity> productOptional = productRepository.findByProductCode("P115");
        assertThat(productOptional).isNotNull();
        ProductEntity foundProduct = productOptional.get();
        assertThat(foundProduct.getId()).isNotNull();
        assertThat(foundProduct.getCode()).isEqualTo("P115");
        assertThat(foundProduct.getName()).isEqualTo("Fifty Shades of Grey");
        assertThat(foundProduct.getDescription())
                .isEqualTo(
                        "When literature student Anastasia Steele goes to interview young entrepreneur Christian Grey, she encounters a man who is beautiful, brilliant, and intimidating.");
        assertThat(foundProduct.getImageUrl()).isEqualTo("https://images.gr-assets.com/books/1385207843l/10818853.jpg");
        assertThat(foundProduct.getPrice()).isEqualTo(BigDecimal.valueOf(97.0));
    }
}
