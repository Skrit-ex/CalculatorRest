package com.example.calculatorrest.repository;

import com.example.calculatorrest.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
