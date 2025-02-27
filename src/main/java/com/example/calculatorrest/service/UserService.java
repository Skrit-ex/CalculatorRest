package com.example.calculatorrest.service;

import com.example.calculatorrest.config.SecurityConfig;
import com.example.calculatorrest.entity.User;
import com.example.calculatorrest.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public Optional<List<User>> findAll(){
        List<User> userList = userRepository.findAll();
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
    public Optional<User> findByUsername (String username){
        Optional<User> findUsername = userRepository.findByUsername(username);
        if(findUsername.isEmpty()){
            return Optional.empty();
        }
        return findUsername;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional <User> user = userRepository.findByUsername(username);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("User not found with username : " + username);
        }
        User user1 = user.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user1.getUsername())
                    .password(user1.getPassword())
                    .roles(user1.getRoles().toArray(new String[0]))
                    .build();
    }
}
