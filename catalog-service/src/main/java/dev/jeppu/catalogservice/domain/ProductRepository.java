package dev.jeppu.catalogservice.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query("select p from ProductEntity p where p.code = :code")
    Optional<ProductEntity> findByProductCode(@Param("code") String pageCode);
}
