package com.example.twm.responses;

import com.example.twm.jwt.JwtResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private JwtResponse jwtResponse;
    private String username;
    private Long id;
}
