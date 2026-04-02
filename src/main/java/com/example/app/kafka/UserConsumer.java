package com.example.app.kafka;

import com.example.app.event.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {

    private static final Logger log = LoggerFactory.getLogger(UserConsumer.class);

    @KafkaListener(topics = "user-topic", groupId = "user-group")
    public void consume(UserEvent event) {

        log.info("Received event from Kafka: {}", event);

        // simulate processing
        log.info("Processing user: {}", event.getEmail());
    }
}