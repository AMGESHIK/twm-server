package com.example.twm.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse {
    Long userId;
    String username;
    String name;
    String about;
    String email;
    String mobile;
    String address;
}
