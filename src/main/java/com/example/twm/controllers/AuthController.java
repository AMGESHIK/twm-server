package com.example.twm.controllers;


import com.example.twm.domain.Role;
import com.example.twm.domain.User;
import com.example.twm.jwt.AuthService;
import com.example.twm.jwt.JwtRequest;
import com.example.twm.jwt.JwtResponse;
import com.example.twm.jwt.RefreshJwtRequest;
import com.example.twm.responses.LoginResponse;
import com.example.twm.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {
    private final AuthService authService;

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody JwtRequest authRequest) {
        final LoginResponse loginResponse = authService.login(authRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("registration")
    public ResponseEntity<String> registration(@RequestBody User user) {
        if (!userService.userExist(user)) {
            user.setActive(true);
            user.setPhoto("default_image.jpeg");
            user.setRoles(Collections.singleton(Role.USER));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
            return ResponseEntity.ok("Successfully");
        } else {
            return ResponseEntity.badRequest().body("User with this email or username exist already");
        }
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        if(token.getAccessToken()==null){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(token);
    }

//    @PostMapping("refresh")
//    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
//        final JwtResponse token = authService.refresh(request.getRefreshToken());
//        return ResponseEntity.ok(token);
//    }

}
