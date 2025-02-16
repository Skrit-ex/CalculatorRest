package com.example.calculatorrest.service;

import com.example.calculatorrest.entity.Operation;
import com.example.calculatorrest.repository.CalculatorRepository;

public class DivOperation implements CalculatorRepository {

    private final Operation operation;

    public DivOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public void process() {
       operation.setResult(operation.getNum1()+ operation.getNum2());
    }

    @Override
    public Operation getFinalResult() {
        return operation;
    }
}
