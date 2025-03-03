package com.example.calculatorrest.service;

import com.example.calculatorrest.config.SecurityConfig;
import com.example.calculatorrest.entity.User;
import com.example.calculatorrest.repository.UserRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public Optional<List<User>> findAll() {
        List<User> userList = userRepository.findAll();
        return Optional.of(userList);
    }

    public Optional<User> findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            log.info("User with id " + id + " was found");
            return optionalUser;
        } else {
            log.error("user id not found");
            return Optional.empty();
        }
    }

    public Optional<User> saveUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if (userOptional.isPresent()) {
            return Optional.empty();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of("ROLE_USER"));
        userRepository.save(user);
        return Optional.of(user);
    }

    public Optional<User> saveAdmin(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is empty/null");
        }
        Optional<User> newUser = userRepository.findByUsername(user.getUsername());
        if (newUser.isPresent()) {
            return Optional.empty();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of("ROLE_ADMIN"));
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

    public Optional<User> deleteUserByUsername(String username) {
        Optional<User> optionalUserName = userRepository.findByUsername(username);
        if (optionalUserName.isEmpty()) {
            log.error("user with username " + username + " not found");
            return Optional.empty();
        }
        userRepository.delete(optionalUserName.get());
        log.info("User with username " + username + " was deleted");
        return optionalUserName;
    }

    public Optional<User> findByUsername(String username) {
        Optional<User> findUsername = userRepository.findByUsername(username);
        if (findUsername.isEmpty()) {
            return Optional.empty();
        }
        return findUsername;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username : " + username);
        }
        User user1 = user.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user1.getUsername())
                .password(user1.getPassword())
                .roles(user1.getRoles().toArray(new String[0]))
                .build();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            log.error("authentication is authenticated " + authentication.isAuthenticated());
            log.error("principal " + authentication.getPrincipal());

            if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
                log.info("User is authenticated");
                log.info("Principal is instance of UserDetails");
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                log.info(userDetails.getUsername() + " username");
                log.info("Authentication user " + userDetails);
                User user = findByUsername(userDetails.getUsername()).orElse(null);
                if (user == null) {
                    log.info("user " + userDetails.getUsername());
                }
                return user;
            } else if (authentication.getPrincipal() instanceof String) {
                log.warn("Principal is a string: " + authentication.getPrincipal());
                String username = (String) authentication.getPrincipal();
                User user = findByUsername(username).orElse(null);
                if (user == null) {
                    log.error("Error, user not found " + username);
                }
                return user;
            } else {
                log.error("Principal is of unknown type: " + authentication.getPrincipal().getClass().getName());
            }
        }
        log.warn("Authentication is null or not authenticated");
        return null;
    }

    public void loginAdmin(User user){
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());

        if(optionalUser.isEmpty()){
            log.error("Error not found or field is empty");
            return;
        }
        User user1 = optionalUser.get();
        if(!passwordEncoder.matches(user.getPassword(), user1.getPassword())){
            log.error("Invalid password");
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("User authentication successful");
        }
    }

