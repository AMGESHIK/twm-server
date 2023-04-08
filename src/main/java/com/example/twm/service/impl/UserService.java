package com.example.twm.service.impl;

import com.example.twm.domain.User;
import com.example.twm.repos.UserRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepo.findByUsername(username);
    }

    public Optional<User> getByEmail(@NonNull String email) {
        return userRepo.findByEmail(email);
    }

    public void saveUser(User user){
        userRepo.save(user);
    }

    public boolean userExist(User user){
        return userRepo.findByEmail(user.getEmail()).isPresent()
                && userRepo.findByUsername(user.getUsername()) != null;
    }

}
