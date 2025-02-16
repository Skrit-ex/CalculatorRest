package com.example.calculatorrest.repository;

import com.example.calculatorrest.entity.Operation;

public interface CalculatorRepository {

    void process();

    Operation getFinalResult();
}
