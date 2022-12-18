package com.example.twm.service.impl;


import com.example.twm.domain.Program;
import com.example.twm.domain.ProgramComposition;
import com.example.twm.repos.ProgramCompositionRepo;
import com.example.twm.service.ProgramCompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramCompositionServiceImpl implements ProgramCompositionService {

    @Autowired
    ProgramCompositionRepo programCompositionRepo;

    @Override
    public void save(ProgramComposition programComposition) {
        programCompositionRepo.save(programComposition);
    }

    @Override
    public List<ProgramComposition> getProgramsCompositionOfThisProgram(Program program) {
        return programCompositionRepo.findProgramCompositionsByProgramOrderById(program);
    }

    @Override
    public void deleteAllByProgram(Program program) {
        programCompositionRepo.deleteAllByProgram(program);
    }
}
