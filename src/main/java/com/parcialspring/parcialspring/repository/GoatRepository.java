package com.parcialspring.parcialspring.repository;

import com.parcialspring.parcialspring.model.GoatModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoatRepository extends JpaRepository<GoatModel, Long> {
    Optional<GoatModel> findByGoatId(String goatId);
}

