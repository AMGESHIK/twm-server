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
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    //    Optional<ChatRoom> findBySenderIdAndRecipientId(Long senderId, Long recipientId);
//    Optional<ChatRoom> findBySenderId (Long senderId);
//    Optional<ChatRoom> findByUsers(List<User> users);
    @Query("SELECT cr FROM ChatRoom cr JOIN cr.users u WHERE u IN :users GROUP BY cr HAVING COUNT(DISTINCT u) = :userCount")
    Optional<ChatRoom> findByUsers(@Param("users") List<User> users, @Param("userCount") long userCount);
    List<ChatRoom> findAllByUsers(User user);
//List<ChatRoom> findBy

}
