package com.parcialspring.parcialspring.repository;

import com.parcialspring.parcialspring.model.ProductOutputModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOutputRepository extends JpaRepository<ProductOutputModel, Long> {
}

