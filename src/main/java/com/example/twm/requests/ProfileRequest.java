package com.example.twm.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileRequest {
    String name;
    String about;
    String email;
    String mobile;
    String address;
}
