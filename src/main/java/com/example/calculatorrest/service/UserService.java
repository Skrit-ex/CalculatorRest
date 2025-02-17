package com.example.calculatorrest.service;

import com.example.calculatorrest.entity.User;
import com.example.calculatorrest.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private List<User> userList = new ArrayList<>();
    @Autowired
    private UserRepository userRepository;

    public Optional<List<User>> findAll(){
        userList = userRepository.findAll();
        return Optional.of(userList);
    }
    public Optional<User> findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()){
            log.info("User with id " + id + " was found");
            return optionalUser;
        }else {
            log.error("user id not found");
            return Optional.empty();
        }
    }

    public Optional<User> saveUser(User user){
        userRepository.save(user);
        return Optional.of(user);
    }

    public Optional<User> deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            log.info("User was deleted");
            return optionalUser;
        } else {
            log.error("User with id " + id + " was not found");
            return Optional.empty();
        }
    }
}
