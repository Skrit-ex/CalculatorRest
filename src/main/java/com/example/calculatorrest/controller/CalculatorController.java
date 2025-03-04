package com.example.calculatorrest.controller;

import com.example.calculatorrest.entity.Operation;
import com.example.calculatorrest.exception.FilesNotFoundException;
import com.example.calculatorrest.repository.OperationRepository;
import com.example.calculatorrest.service.CalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
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

    @GetMapping("/history")
    public ResponseEntity<List<Operation>> getHistory() {
        Optional<List<Operation>> allOperation = calculatorService.getAllOperation();

        return allOperation
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.error("Files not found in baseDate");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });

    }
}
