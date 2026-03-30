package com.example.app.controller;

import com.example.app.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        // hardcoded validation for now
        if ("admin".equals(username) && "admin123".equals(password)) {

            String token = jwtUtil.generateToken(username);

            return Map.of("token", token);
        }

        throw new RuntimeException("Invalid credentials");
    }
}