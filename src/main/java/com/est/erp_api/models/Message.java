package com.est.erp_api.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
public class Message {
    @Id
    private Long id;
    private String content;
    private String senderId; // Changed to String
    private String receiverId; // Changed to String
    private LocalDateTime timestamp;
}