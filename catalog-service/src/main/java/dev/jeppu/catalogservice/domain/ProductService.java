package dev.jeppu.catalogservice.domain;

import dev.jeppu.catalogservice.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ApplicationProperties properties;

    public PagedResponse<Product> getAllProducts(int pageNo) {
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNo, properties.getPageSize());
        Page<Product> productPage = productRepository.findAll(pageable).map(ProductMapper::mapToDTO);
        PagedResponse<Product> pagedResponse = new PagedResponse<Product>(
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.getNumber() + 1,
                productPage.isFirst(),
                productPage.isLast(),
                productPage.hasNext(),
                productPage.hasPrevious(),
                productPage.getContent());
        return pagedResponse;
    }

    public Product getProductByCode(String productCode) {
        Product foundProduct = productRepository
                .findByProductCode(productCode)
                .map(ProductMapper::mapToDTO)
                .orElseThrow(() -> new ProductNotFoundException(productCode));
        return foundProduct;
    }
}
