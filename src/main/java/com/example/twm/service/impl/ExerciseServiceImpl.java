package com.example.twm.service.impl;

import com.example.twm.domain.Exercise;
import com.example.twm.repos.ExerciseRepo;
import com.example.twm.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepo exerciseRepo;

    @Override
    public List<Exercise> findAll() {
        return exerciseRepo.findAll();
    }

    @Override
    public Exercise findById(Long id) {
        return exerciseRepo.findExerciseById(id);
    }
}
