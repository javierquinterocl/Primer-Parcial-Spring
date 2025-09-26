package com.parcialspring.parcialspring.repository;

import com.parcialspring.parcialspring.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByCode(String code);
    Optional<UserModel> findByIdCard(String idCard);
}
