package com.example.twm.requests;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ProgramCompositionRequest {
    private int numberTraining;
    private int amountsOfSets;
    private Long exercise;
    private int amountsOfRepetitions;
}
