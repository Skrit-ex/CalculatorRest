package com.example.calculatorrest.entity;

import lombok.Data;

import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Table(name = "user_operation")
@Entity
@Data
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
}
