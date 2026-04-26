package dev.jeppu.catalogservice.domain;

public class ProductMapper {
    public static Product mapToDTO(ProductEntity entity) {
        Product product = new Product(
                entity.getCode(), entity.getName(), entity.getDescription(), entity.getImageUrl(), entity.getPrice());
        return product;
    }
}
