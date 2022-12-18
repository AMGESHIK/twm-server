package com.example.twm.requests;

import lombok.Data;

import java.util.List;

@Data
public class ProgramRequest {
    private String name;
    private String description;
    private List<ProgramCompositionRequest> programComposition;
}
