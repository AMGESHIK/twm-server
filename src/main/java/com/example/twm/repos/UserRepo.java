package com.example.twm.repos;

import com.example.twm.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String Username);
    Optional<User> findByEmail(String Email);
}
