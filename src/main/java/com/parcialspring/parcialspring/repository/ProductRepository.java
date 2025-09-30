package com.parcialspring.parcialspring.repository;

import com.parcialspring.parcialspring.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    Optional<ProductModel> findByProductId(String productId);
    Optional<ProductModel> findByName(String name);
}
