package com.example.twm.responses;

import com.example.twm.domain.User;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ChatMessageResponse {
    private Long id;
    private Long sender;
    private String content;
    private Long timestamp;
}
