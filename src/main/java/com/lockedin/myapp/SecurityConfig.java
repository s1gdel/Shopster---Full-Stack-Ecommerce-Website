package com.lockedin.myapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", 
                    "/signup", 
                    "/custom-login", 
                    "/forgot-password", 
                    "/reset-password",
                    "/products", 
                    "/sell", 
                    "/cart",
                    "/cart.css",
                    "/cart/add",
                    "/cart/buy", 
                    "/cart/success", 
                    "/cart/cancel", 
                    "/cart/remove",
                    "/styles.css", 
                    "/products.css", 
                    "/scripts.js",
                    "/home.html",
                    "/sell.css",
                    "/sell.html",
                    "/profile",
                    "/profile.css",
                    "/profile.html",
                    "/logout1",
                    "/uploads/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            
            .csrf(csrf -> csrf.disable()); // Disable CSRF for simplicity (be cautious in production)

        return http.build();
    }
}
