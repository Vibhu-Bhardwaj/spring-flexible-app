package com.example.app.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent {
    private String name;
    private String email;
}