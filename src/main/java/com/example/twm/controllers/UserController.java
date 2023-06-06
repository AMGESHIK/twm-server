package com.example.twm.controllers;

import com.example.twm.domain.User;
import com.example.twm.jwt.AuthService;
import com.example.twm.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUserId() {
        return ResponseEntity.ok(authService.getAuthInfo().getPrincipal());
    }

    @GetMapping("/searchUsers")
    public ResponseEntity<?> searchUsers(@RequestParam String username) {
        List<User> users = userService.findAllByUsernameContaining(username);
        List<HashMap<String, String>> responseUsers = new ArrayList<>();
        for (User user : users) {
            if (!user.getUsername().equals(authService.getAuthInfo().getName())) {
                HashMap<String, String> responseUser = new HashMap<>();
                responseUser.put("id", String.valueOf(user.getId()));
                responseUser.put("username", user.getUsername());
                responseUser.put("name", user.getName());
                responseUsers.add(responseUser);
            }
        }
        return ResponseEntity.ok(responseUsers);
    }

    @GetMapping("/usernameById")
    public ResponseEntity<?> getUsernameById(@RequestParam Long id) {
        if (userService.findById(id).isPresent()) {
            return ResponseEntity.ok(userService.findById(id).get().getUsername());
        }
        return ResponseEntity.badRequest().body("Нет пользователя с таким id");
    }



}
