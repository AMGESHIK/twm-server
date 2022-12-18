package com.example.twm.repos;

import com.example.twm.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepo extends JpaRepository<Program, Long> {

    List<Program> findByUserIdOrderById(Long userId);
    Program findTopByUserIdOrderByIdDesc(Long UserId);
    Program findProgramById(Long id);
    void deleteProgramById(Long id);
}
