package com.example.calculatorrest.entity;

import lombok.Data;

import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles;
}
