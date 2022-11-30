package com.example.twm.repos;

import com.example.twm.domain.Program;
import com.example.twm.domain.ProgramComposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramCompositionRepo extends JpaRepository<ProgramComposition, Long> {
    List<ProgramComposition> findProgramCompositionsByProgramOrderById(Program program);
}
