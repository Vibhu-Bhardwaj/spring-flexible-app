package com.example.app.controller;

import com.example.app.config.CorrelationFilter;
import com.example.app.dto.*;
import com.example.app.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ApiResponse<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO dto) {

        UserResponseDTO data = userService.save(dto);

        return ApiResponse.<UserResponseDTO>builder()
                .success(true)
                .message("User created successfully")
                .data(data)
                .timestamp(LocalDateTime.now())
                .correlationId(MDC.get(CorrelationFilter.CORRELATION_ID))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponseDTO>> getAllUsers() {

        List<UserResponseDTO> data = userService.getAll();

        return ApiResponse.<List<UserResponseDTO>>builder()
                .success(true)
                .message("Users fetched successfully")
                .data(data)
                .timestamp(LocalDateTime.now())
                .correlationId(MDC.get(CorrelationFilter.CORRELATION_ID))
                .build();
    }
}