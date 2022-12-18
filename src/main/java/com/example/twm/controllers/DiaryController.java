package com.example.twm.controllers;

import com.example.twm.domain.Exercise;
import com.example.twm.domain.Program;
import com.example.twm.domain.ProgramComposition;
import com.example.twm.jwt.AuthService;
import com.example.twm.requests.ProgramCompositionRequest;
import com.example.twm.requests.ProgramRequest;
import com.example.twm.service.ExerciseService;
import com.example.twm.service.ProgramCompositionService;
import com.example.twm.service.ProgramService;
import com.example.twm.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("diary")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DiaryController {

    private final ProgramService programService;
    private final AuthService authService;
    private final ExerciseService exerciseService;
    private final ProgramCompositionService programCompositionService;
    private final UserService userService;


    @GetMapping()
    public ResponseEntity<List<Program>> programs() {
        return ResponseEntity.ok(programService.getByUsername(authService.getAuthInfo().getName()));
    }

    @GetMapping("exercises")
    public ResponseEntity<List<Exercise>> exercises() {
        return ResponseEntity.ok(exerciseService.findAll());
    }

    @PostMapping("program")
    public ResponseEntity<String> addProgram(@RequestBody ProgramRequest programRequest) {
        Program program = new Program();
        program.setName(programRequest.getName());
        program.setDescription(programRequest.getDescription());
        program.setPhotoURL("/img/dumbell.png");
        program.setUser(userService.loadUserByUsername(authService.getAuthInfo().getName()));
        programService.save(program);

        for (ProgramCompositionRequest compRequest : programRequest.getProgramComposition()) {
            ProgramComposition programComposition = new ProgramComposition();
            programComposition.setExercise(exerciseService.findById(compRequest.getExercise()));
            programComposition.setNumberTraining(compRequest.getNumberTraining());
            programComposition.setAmountsOfSets(compRequest.getAmountsOfSets());
            programComposition.setAmountsOfRepetition(compRequest.getAmountsOfRepetitions());
            programComposition.setProgram(program);
            programCompositionService.save(programComposition);
        }
        return ResponseEntity.ok("Successfully");
    }

    @GetMapping("program")
    public ResponseEntity<List<ProgramComposition>> getProgram(@RequestParam Long id) {
        Program program = programService.getById(id);
        List<ProgramComposition> programComposition = programCompositionService.getProgramsCompositionOfThisProgram(program);
        return ResponseEntity.ok(programComposition);
    }

    @DeleteMapping("program")
    @Transactional
    public ResponseEntity<String> deleteProgram(@RequestParam Long id) {
        Program program = programService.getById(id);
        if (program.getUser().getUsername().equals(authService.getAuthInfo().getName())) {
            programService.deleteProgram(id);
            return ResponseEntity.ok("Программа удалена");
        }
        return ResponseEntity.badRequest().body("Программа не принадлежит этому пользователю");
    }

    @PutMapping("program/{id}")
    @Transactional
    public ResponseEntity<String> putProgram(@RequestBody ProgramRequest programRequest, @PathVariable Long id) {
        Program program = programService.getById(id);
        if (program.getUser().getUsername().equals(authService.getAuthInfo().getName())) {
            programCompositionService.deleteAllByProgram(program);
            program.setName(programRequest.getName());
            program.setDescription(programRequest.getDescription());
            for (ProgramCompositionRequest compRequest : programRequest.getProgramComposition()) {
                ProgramComposition programComposition = new ProgramComposition();
                programComposition.setExercise(exerciseService.findById(compRequest.getExercise()));
                programComposition.setNumberTraining(compRequest.getNumberTraining());
                programComposition.setAmountsOfSets(compRequest.getAmountsOfSets());
                programComposition.setAmountsOfRepetition(compRequest.getAmountsOfRepetitions());
                programComposition.setProgram(program);
                programCompositionService.save(programComposition);
            }
            return ResponseEntity.ok("Программа обновлена");
        }
        return ResponseEntity.badRequest().body("Программа не принадлежит этому пользователю");
    }


}
