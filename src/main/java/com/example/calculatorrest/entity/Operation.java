package com.example.calculatorrest.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;


import javax.persistence.*;

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
    @Enumerated(EnumType.STRING)
    private OperationType type;
}
