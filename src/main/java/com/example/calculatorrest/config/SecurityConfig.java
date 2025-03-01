package com.example.calculatorrest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
            httpSecurity
                    .csrf().disable()
                    .authorizeRequests(authorizeRequests ->
                            authorizeRequests
                                    .antMatchers("/user/**").permitAll()
                                    .antMatchers("/admin/**").hasRole("ADMIN")
                                    .anyRequest().authenticated()
                    )
                    .httpBasic();
            return httpSecurity.build();
        }
        @Bean
    public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

    }
