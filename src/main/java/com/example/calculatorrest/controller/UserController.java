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

    @Operation(description = "SaveUser")
    @PostMapping("/saveUser")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        userService.saveUser(user);
        log.info("User " + user.getUsername() + " was saved");
        return ResponseEntity.ok(user);
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
        return ResponseEntity.ok(userService.findAll());
      }
    }
