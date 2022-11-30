package com.example.twm.service.impl;

import com.example.twm.domain.Program;
import com.example.twm.repos.ProgramRepo;
import com.example.twm.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    private ProgramRepo programRepo;

    @Override
    public List<Program> getByUserId(Long userId){
        return programRepo.findByUserId(userId);
    }

    @Override
    public void save(Program program) {
        programRepo.save(program);
    }

    @Override
    public Program getLastAddedProgram(Long userId) {
        return programRepo.findTopByUserIdOrderByIdDesc(userId);
    }

    @Override
    public Program getById(Long id) {
        return programRepo.findProgramById(id);
    }
}
