package dev.jeppu.catalogservice.web;

import dev.jeppu.catalogservice.domain.PagedResponse;
import dev.jeppu.catalogservice.domain.Product;
import dev.jeppu.catalogservice.domain.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PagedResponse<Product>> getAllProducts(
            @RequestParam(name = "page", required = false, defaultValue = "1") int pageNo) {
        PagedResponse<Product> allProducts = productService.getAllProducts(pageNo);
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Product> getProductByCode(@PathVariable("code") String pageCode) {
        Product product = productService.getProductByCode(pageCode);
        return new ResponseEntity<>(product, HttpStatus.FOUND);
    }
}
