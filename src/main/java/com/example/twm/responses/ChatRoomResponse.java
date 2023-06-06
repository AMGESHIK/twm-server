package com.example.twm.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoomResponse {
    Long chatId;
    Long userId;
    String username;
    ChatMessageResponse lastMessage;

}
