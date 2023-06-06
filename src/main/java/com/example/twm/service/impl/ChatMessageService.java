package com.example.twm.service.impl;

import com.example.twm.domain.chat.ChatMessage;
import com.example.twm.domain.chat.ChatRoom;
import com.example.twm.exception.ResourceNotFoundException;
import com.example.twm.repos.ChatMessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepo repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        repository.save(chatMessage);
        return chatMessage;
    }

//    public long countNewMessages(String senderId, String recipientId) {
//        return repository.countBySenderIdAndRecipientIdAndStatus(
//                senderId, recipientId, MessageStatus.RECEIVED);
//    }
//
    public List<ChatMessage> findChatMessages(Long senderId, Long recipientId) {
        var chatId = chatRoomService.getChatRoom(senderId, recipientId, false);
        var messages =
                chatId.map(repository::findAllByChatRoomOrderByTimestampAsc).orElse(new ArrayList<>());
        return messages;
    }

    public Optional<ChatMessage> findLastMessageInChatRoom(ChatRoom chatRoom){
        return repository.findFirstByChatRoomOrderByTimestampDesc(chatRoom);
    }



//    public ChatMessage findById(String id) {
//        return repository
//                .findById(id)
//                .map(chatMessage -> {
//                    chatMessage.setStatus(MessageStatus.DELIVERED);
//                    return repository.save(chatMessage);
//                })
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("can't find message (" + id + ")"));
//    }
}
