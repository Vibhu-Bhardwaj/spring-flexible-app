package com.example.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor // Required for Jackson
@AllArgsConstructor // Required for Builder
public class UserResponseDTO implements Serializable {
    private Long id;
    private String name;
    private String email;
}