package com.example.app.kafka;

import com.example.app.event.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {

    private static final Logger log = LoggerFactory.getLogger(UserProducer.class);

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public UserProducer(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserEvent(UserEvent event) {

        log.info("Sending event to Kafka: {}", event);

        kafkaTemplate.send("user-topic", event);
    }
}