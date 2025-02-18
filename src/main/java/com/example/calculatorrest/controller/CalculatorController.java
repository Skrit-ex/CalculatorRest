package com.example.calculatorrest.controller;

import com.example.calculatorrest.entity.Operation;
import com.example.calculatorrest.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/calc")
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @PostMapping("/calculate")
    public ResponseEntity<Operation> calculate(@RequestBody Operation operation) {
        Optional<Operation> operation1 = calculatorService.calculate(operation);
        return operation1.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
