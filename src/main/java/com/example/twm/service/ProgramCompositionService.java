package com.example.twm.service;

import com.example.twm.domain.Program;
import com.example.twm.domain.ProgramComposition;

import java.util.List;

public interface ProgramCompositionService {
    void save(ProgramComposition programComposition);
    List<ProgramComposition> getProgramsCompositionOfThisProgram(Program program);
    void deleteAllByProgram (Program program);
}
