package com.example.calculatorrest.repository;

import com.example.calculatorrest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
