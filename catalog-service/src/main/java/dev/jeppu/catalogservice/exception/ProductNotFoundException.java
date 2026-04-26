package dev.jeppu.catalogservice.exception;

public class ProductNotFoundException extends RuntimeException {
    private String productCode;

    public ProductNotFoundException(String productCode) {
        super(String.format("Product code : %s not found", productCode));
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
