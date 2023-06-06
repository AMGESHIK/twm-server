package com.example.twm.repos;

import com.example.twm.domain.User;
import com.example.twm.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);
    List<User> findAllByUsernameContainingIgnoreCase(String username);
    Optional<User> findByEmail(String email);
    @Query("SELECT DISTINCT u FROM User u JOIN u.chatRooms cr WHERE cr IN :chatRooms AND u.id <> :currentUserId")
    List<User> findUsersByChatRoomsExcludingCurrentUser(@Param("chatRooms") List<ChatRoom> chatRooms, @Param("currentUserId") Long currentUserId);
}
