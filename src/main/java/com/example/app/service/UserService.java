package com.example.app.service;

import com.example.app.dto.UserRequestDTO;
import com.example.app.dto.UserResponseDTO;
import com.example.app.entity.User;
import com.example.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * CREATE USER → CLEAR CACHE
     */
    @CacheEvict(value = "users", allEntries = true)
    public UserResponseDTO save(UserRequestDTO dto) {

        log.info("Saving user and clearing cache. Email: {}", dto.getEmail());

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        User saved = userRepository.save(user);

        log.info("User created with ID: {}", saved.getId());

        return mapToResponse(saved);
    }

    /**
     * GET ALL USERS → CACHE RESULT
     */
    @Cacheable(value = "users")
    public List<UserResponseDTO> getAll() {

        log.info("Fetching users from DB (cache miss)");

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * MAPPER METHOD
     */
    private UserResponseDTO mapToResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}