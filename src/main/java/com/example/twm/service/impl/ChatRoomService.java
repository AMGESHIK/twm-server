package com.example.twm.service.impl;

import com.example.twm.domain.User;
import com.example.twm.domain.chat.ChatRoom;
import com.example.twm.repos.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

    public Optional<ChatRoom> getChatRoom(Long senderId, Long recipientId, boolean createIfNotExist) {
        User sender = userService.findById(senderId).get();
        User recipient = userService.findById(recipientId).get();
        return chatRoomRepository
                .findByUsers(Arrays.asList(sender, recipient), 2)
                .or(() -> {
                    if (!createIfNotExist) {
                        return Optional.empty();
                    }

                    ChatRoom newChatRoom = ChatRoom
                            .builder()
                            .users(Arrays.asList(sender, recipient))
                            .build();

                    chatRoomRepository.save(newChatRoom);

                    return Optional.of(newChatRoom);
                });
    }
    public List<ChatRoom> getChatRooms(Long userId){
        User user = userService.findById(userId).get();
        return chatRoomRepository.findAllByUsers(user);
//        System.out.println(chatRoomRepository.findAllByUsers(user).toString());

    }
}
