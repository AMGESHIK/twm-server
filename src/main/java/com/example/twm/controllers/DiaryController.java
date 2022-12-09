package com.example.twm.controllers;

import com.example.twm.domain.Program;
import com.example.twm.jwt.AuthService;
import com.example.twm.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("diary")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:8081")
public class DiaryController {

    private final ProgramService programService;
    private final AuthService authService;


    @GetMapping()
    public ResponseEntity<List<Program>> programs(){
        return ResponseEntity.ok(programService.getByUsername(authService.getAuthInfo().getName()));
    }
}
