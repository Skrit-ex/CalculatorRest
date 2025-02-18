package com.example.calculatorrest.factory;

import com.example.calculatorrest.entity.Operation;
import com.example.calculatorrest.repository.CalculatorRepository;
import com.example.calculatorrest.service.DifOperation;
import com.example.calculatorrest.service.DivOperation;
import com.example.calculatorrest.service.MulOperation;
import com.example.calculatorrest.service.SumOperation;

import java.util.Optional;


public abstract class OperationFactory {

    public static Optional<CalculatorRepository> createOperation(Operation operation){
        return switch (operation.getType()) {
            case SUM -> Optional.of(new SumOperation(operation));
            case MUL -> Optional.of(new MulOperation(operation));
            case DIV -> Optional.of(new DivOperation(operation));
            case DIF -> Optional.of(new DifOperation(operation));
        };
    }
}
