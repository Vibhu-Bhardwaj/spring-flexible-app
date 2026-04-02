package com.example.app.service;

import com.example.app.dto.UserRequestDTO;
import com.example.app.dto.UserResponseDTO;
import com.example.app.entity.User;
import com.example.app.event.UserEvent;
import com.example.app.kafka.UserProducer;
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
    private final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    @CacheEvict(value = "users", allEntries = true)
    public UserResponseDTO save(UserRequestDTO dto) {

        log.info("Saving user and sending event");

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        User saved = userRepository.save(user);

        // 🔥 SEND EVENT TO KAFKA
        UserEvent event = new UserEvent(saved.getName(), saved.getEmail());
        userProducer.sendUserEvent(event);

        return mapToResponse(saved);
    }

    @Cacheable(value = "users")
    public List<UserResponseDTO> getAll() {

        log.info("Fetching users from DB");

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private UserResponseDTO mapToResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}