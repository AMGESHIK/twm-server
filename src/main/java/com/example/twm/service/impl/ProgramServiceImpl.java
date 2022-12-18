package com.example.twm.service.impl;

import com.example.twm.domain.Program;
import com.example.twm.repos.ProgramRepo;
import com.example.twm.repos.UserRepo;
import com.example.twm.service.ProgramCompositionService;
import com.example.twm.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepo programRepo;
    private final UserRepo userRepo;
    private final ProgramCompositionService programCompositionService;


    @Override
    public List<Program> getByUserId(Long userId) {
        return programRepo.findByUserIdOrderById(userId);
    }

    @Override
    public List<Program> getByUsername(String username) {
        Long id = userRepo.findByUsername(username).getId();
        return programRepo.findByUserIdOrderById(id);
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

    @Override
    public void deleteProgram(Long id) {
        programRepo.deleteProgramById(id);
    }

}
