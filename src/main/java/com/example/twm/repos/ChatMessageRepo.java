package com.example.twm.repos;

import com.example.twm.domain.chat.ChatMessage;
import com.example.twm.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepo extends JpaRepository<ChatMessage, String> {
//    Long countBySenderIdAndRecipientIdAndStatus(String senderId, String recipientId);
//    List<ChatMessage> findByChatId(String chatId);
List<ChatMessage> findAllByChatRoomOrderByTimestampAsc(ChatRoom chatRoom);
Optional<ChatMessage> findFirstByChatRoomOrderByTimestampDesc(ChatRoom chatRoom);
}
