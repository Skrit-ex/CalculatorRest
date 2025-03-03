package com.example.calculatorrest.controller;

import com.example.calculatorrest.exception.ResourceNotFoundException;
import com.example.calculatorrest.entity.User;
import com.example.calculatorrest.repository.UserRepository;
import com.example.calculatorrest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    private final Map<String, byte[]> avatars = new HashMap<>();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(description = "registerUser")
    @PostMapping("/saveUser")
    public ResponseEntity<String> saveUser(@RequestBody User user){
        userService.saveUser(user);
        log.info("User " + user.getUsername() + " was saved");
        return ResponseEntity.ok("user saved with id->" + user.getId());
    }
    @PostMapping("/saveUser/key")
    public ResponseEntity<String> saveUs(@RequestBody User user){
        userService.saveAdmin(user);
        return ResponseEntity.ok("UserAdmin was saved");
    }

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
    public ResponseEntity<Object> findAll(){
        log.info("Show all users");
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(description = "Login")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        Optional <User> userFindByUsername = userService.findByUsername(user.getUsername());
        if(userFindByUsername.isEmpty()){
            return ResponseEntity.status(404).body("User not found with username " + user.getUsername());
        }else {
            User foundUser = userFindByUsername.get();
            if(passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_USER")));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                return ResponseEntity.ok("Login successful");
            }else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        }
    }
    @PostMapping("/login/key")
    public ResponseEntity<?> loginAdmin(@RequestBody User user){
        userService.loginAdmin(user);
        return ResponseEntity.ok("Admin authenticated successful");
    }
}
