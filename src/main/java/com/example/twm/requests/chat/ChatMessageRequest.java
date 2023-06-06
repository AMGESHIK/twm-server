package com.example.twm.requests.chat;

import com.example.twm.domain.User;
import lombok.Data;

@Data
public class ChatMessageRequest {
    private String content;
    private Long senderId;
    private Long recipientId;
}
