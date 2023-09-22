package com.example.models.courses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity(name = "Course")
@Table(name = "course")
@NoArgsConstructor
@Getter
@Setter
public class Course {
    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private String id;
    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;
    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;
    @Column(
            name = "board_id",
            nullable = false
    )
    private String movesThatTheComputerWillPlay;
    @Column(
            name = "moves_that_the_player_should_play",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String movesThatThePlayerShouldPlay;


    public Course(String name, String description, String movesThatTheComputerWillPlay, String movesThatThePlayerShouldPlay) {
        this.name = name;
        this.description = description;
        this.movesThatTheComputerWillPlay = movesThatTheComputerWillPlay;
        this.movesThatThePlayerShouldPlay = movesThatThePlayerShouldPlay;
    }

}
