package com.example.calculatorrest.controller;

import com.example.calculatorrest.entity.User;
import com.example.calculatorrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<User> saveUser(@RequestBody User user){
        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        Optional<User> deleteUser = userService.deleteUser(id);

        if(deleteUser.isPresent()){
            return ResponseEntity.ok("User was deleted successfully");
        }else {
            return ResponseEntity.status(404).body("User not found");
        }

    }
}
