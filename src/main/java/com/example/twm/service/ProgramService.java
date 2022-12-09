package com.example.twm.service;

import com.example.twm.domain.Program;

import java.util.List;

public interface ProgramService {
    List<Program> getByUserId(Long userId);
    List<Program> getByUsername(String username);
    Program getLastAddedProgram(Long userId);
    void save(Program program);
    Program getById(Long id);
}
