package com.example.twm.domain;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "muscles")
    private String muscles;
}
