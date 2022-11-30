package com.example.twm.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    USER,
    PREM_USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
