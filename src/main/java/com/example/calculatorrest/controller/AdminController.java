package com.example.calculatorrest.controller;

import com.example.calculatorrest.entity.User;
import com.example.calculatorrest.exception.ResourceNotFoundException;
import com.example.calculatorrest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/currentUser")
    public ResponseEntity<User> currentUser1() {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            return ResponseEntity.ok(currentUser);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @Operation(description = "Delete user by username")
    @DeleteMapping("/deleteUser/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        Optional<User> user = userService.deleteUserByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User with username : '" + username + "' not found");
        } else {
            return ResponseEntity.ok("User deleted successful");
        }
    }

    @Operation(description = "DeleteUserById")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> deleteUser = userService.deleteUser(id);

        if (deleteUser.isPresent()) {
            return ResponseEntity.ok("User was deleted successfully");
        } else {
            throw new ResourceNotFoundException("User not found with id = " + id);
        }
    }
}
