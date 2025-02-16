package com.example.calculatorrest.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cascade;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "operation")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double num1;
    private Double num2;
    private Double result;
    @OneToOne(cascade = CascadeType.REMOVE)
    private User user;
    private OperationType type;
}
