package com.example.twm.controllers;

import com.example.twm.domain.User;
import com.example.twm.domain.chat.ChatMessage;
import com.example.twm.domain.chat.ChatRoom;
import com.example.twm.jwt.AuthService;
import com.example.twm.requests.chat.ChatMessageRequest;
import com.example.twm.responses.ChatMessageResponse;
import com.example.twm.responses.ChatRoomResponse;
import com.example.twm.service.impl.ChatMessageService;
import com.example.twm.service.impl.ChatRoomService;
import com.example.twm.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final AuthService authService;
    private final UserService userService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageRequest chatMessageRequest) {
        var chatRoom = chatRoomService
                .getChatRoom(chatMessageRequest.getSenderId(), chatMessageRequest.getRecipientId(), true).get();
        ChatMessage chatMessage = ChatMessage
                .builder()
                .chatRoom(chatRoom)
                .content(chatMessageRequest.getContent())
                .sender(userService.findById(chatMessageRequest.getSenderId()).get())
                .timestamp(new Date())
                .build();

        ChatMessage saved = chatMessageService.save(chatMessage);
        ChatMessageResponse chatMessageResponse = ChatMessageResponse
                .builder()
                .id(saved.getId())
                .content(saved.getContent())
                .sender(saved.getSender().getId())
                .timestamp(saved.getTimestamp().getTime())
                .build();

        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessageRequest.getRecipientId()), "/queue/messages", chatMessageResponse);
    }

    @GetMapping("/chats")
    public ResponseEntity<?> findChats() {
        List<ChatRoom> chatRooms = chatRoomService.getChatRooms(authService.getAuthInfo().getId());
        System.out.println(userService.findById(authService.getAuthInfo().getId()).get().getChatRooms());

        List<ChatRoomResponse> chatRoomResponses = new ArrayList<>();
        chatRooms.forEach(x  -> {
            User companion = userService.findUsersByChatRoomsExcludingCurrentUser(Arrays.asList(x), authService.getAuthInfo().getId()).get(0);
            if (companion.getId() != authService.getAuthInfo().getId()) {
                var chatMessageOpt = chatMessageService.findLastMessageInChatRoom(x);
                if (chatMessageOpt.isPresent()) {
                    ChatMessage chatMessage = chatMessageOpt.get();
                    ChatMessageResponse chatMessageResponse = ChatMessageResponse
                            .builder()
                            .id(chatMessage.getId())
                            .timestamp(chatMessage.getTimestamp().getTime())
                            .sender(chatMessage.getSender().getId())
                            .content(chatMessage.getContent())
                            .build();
                    ChatRoomResponse chatRoomResponse = ChatRoomResponse
                            .builder()
                            .chatId(x.getId())
                            .userId(companion.getId())
                            .username(companion.getUsername())
                            .lastMessage(chatMessageResponse)
                            .build();
                    chatRoomResponses.add(chatRoomResponse);
                }
            }
        });
        System.out.println("\n" + chatRoomResponses.toString());
        return ResponseEntity.ok(chatRoomResponses);
    }

    @GetMapping("/messages")
    public ResponseEntity<?> findMessages(@RequestParam String recipientId) {
        List<ChatMessage> chatMessages = chatMessageService.findChatMessages(authService.getAuthInfo().getId(), Long.parseLong(recipientId));
        List<ChatMessageResponse> chatMessagesResponse = new ArrayList<>();
        for (ChatMessage chatMessage : chatMessages) {
            chatMessagesResponse.add(
                    ChatMessageResponse.builder()
                            .id(chatMessage.getId())
                            .sender(chatMessage.getSender().getId())
                            .content(chatMessage.getContent())
                            .timestamp(chatMessage.getTimestamp().getTime())
                            .build()
            );
        }
        return ResponseEntity.ok(chatMessagesResponse);
    }
}
