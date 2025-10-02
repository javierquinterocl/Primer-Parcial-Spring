package com.parcialspring.parcialspring.repository;

import com.parcialspring.parcialspring.model.SupplierModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<SupplierModel, Long> {

    Optional<SupplierModel> findByEmail(String email);

    Optional<SupplierModel> findByNit(String nit);

    Optional<SupplierModel> findBySupplierId(String supplierId);
}