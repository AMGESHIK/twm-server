package com.example.twm.responses;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ChatRoomResponse {
    Long chatId;
    Long userId;
    String username;
    ChatMessageResponse lastMessage;

}
