package com.example.twm.domain.chat;

import com.example.twm.domain.User;
import lombok.*;

import jakarta.persistence.*;

import java.util.List;

@Data
@Entity
@Table(name = "chat_room")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany
    @JoinTable(name = "user_chatroom",
            joinColumns = @JoinColumn(name = "chatroom_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    private List<User> users;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<ChatMessage> chatMessages;
}
