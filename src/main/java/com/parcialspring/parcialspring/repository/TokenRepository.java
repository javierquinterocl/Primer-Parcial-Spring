package com.parcialspring.parcialspring.repository;

import com.parcialspring.parcialspring.model.TokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenModel, Long> {
    Optional<TokenModel> findByToken(String token);
}
