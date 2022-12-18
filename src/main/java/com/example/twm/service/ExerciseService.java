package com.example.twm.service;

import com.example.twm.domain.Exercise;

import java.util.List;

public interface ExerciseService {
    List<Exercise> findAll();
    Exercise findById(Long id);
}
