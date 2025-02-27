package com.example.calculatorrest.controller;

import com.example.calculatorrest.exception.ResourceNotFoundException;
import com.example.calculatorrest.entity.User;
import com.example.calculatorrest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/currentUser")
    public ResponseEntity<User> currentUser1(){
        User currentUser = userService.getCurrentUser();
        if(currentUser != null){
            return ResponseEntity.ok(currentUser);
        }else {
            return ResponseEntity.status(401).build();
        }
    }

    @Operation(description = "registerUser")
    @PostMapping("/saveUser")
    public ResponseEntity<String> saveUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        log.info("User " + user.getUsername() + " was saved");
        return ResponseEntity.ok("user saved with id" + user.getId());
    }
    @Operation(description = "DeleteUser")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        Optional<User> deleteUser = userService.deleteUser(id);

        if(deleteUser.isPresent()){
            return ResponseEntity.ok("User was deleted successfully");
        }else {
            throw new ResourceNotFoundException("User not found with id = " + id);
        }
    }
    private final Map<String, byte[]> avatars = new HashMap<>();

    @SneakyThrows
    @PostMapping("/{username}/upload")
    public ResponseEntity<Void> upAvatar(@PathVariable String username,
                                         MultipartFile file) {
        if(file.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        avatars.put(username, file.getBytes());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/download")
    public ResponseEntity<byte[]> downloadUserAvatar(@PathVariable String username){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE);
        return new ResponseEntity<>(avatars.get(username), httpHeaders, HttpStatus.OK);
    }

    @Operation(description = "FindAllUser")
    @GetMapping("/showUsers")
    public ResponseEntity<?> findAll(){
        log.info("Show all users");
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(description = "Login")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        Optional <User> userFindByUsername = userService.findByUsername(user.getUsername());
        if(userFindByUsername.isEmpty()){
            return ResponseEntity.status(404).body("User not found with username " + user.getUsername());
        }else {
            User foundUser = userFindByUsername.get();
            if(passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                return ResponseEntity.ok("Login successful");
            }else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        }
    }
}
