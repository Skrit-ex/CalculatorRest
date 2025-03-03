package com.example.calculatorrest.service;

import com.example.calculatorrest.entity.Operation;
import com.example.calculatorrest.entity.User;
import com.example.calculatorrest.factory.OperationFactory;
import com.example.calculatorrest.repository.CalculatorRepository;
import com.example.calculatorrest.repository.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Slf4j
public class CalculatorService {
    @Autowired
    private UserService userService;
    @Autowired
    private OperationRepository operationRepository;

    public Optional<Operation> calculate(Operation operation) {

        User user = userService.getCurrentUser();
        if (user != null) {
            operation.setUser(user);
        } else {
            log.error("User not found");
        }
        Optional<CalculatorRepository> optionalCalculatorRepository = OperationFactory.createOperation(operation);
        if (optionalCalculatorRepository.isPresent()) {
            CalculatorRepository calculatorRepository = optionalCalculatorRepository.get();
            calculatorRepository.process();
            if (operation.getUser() != null) {
                operationRepository.save(calculatorRepository.getFinalResult());
            }
            return Optional.of(calculatorRepository.getFinalResult());
        }
        return Optional.empty();
    }

    public Optional<List<Operation>> getAllOperation() {
        List<Operation> all = operationRepository.findAll();
        if (all.isEmpty()) {
            log.error("files not found or other errors");
            return Optional.empty();
        }
        return Optional.of(all);
    }
}
