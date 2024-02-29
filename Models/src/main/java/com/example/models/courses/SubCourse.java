package com.example.models.courses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "SubCourse")
@Table(name = "sub_course")
public class SubCourse {
    @Id
    @SequenceGenerator(
            name = "sub_course_sequence",
            sequenceName = "sub_course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sub_course_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;
    @Column(
            name = "moves_that_the_computer_will_play",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String movesThatTheComputerWillPlay;
    @Column(
            name = "moves_that_the_player_should_play",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String movesThatThePlayerShouldPlay;


    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "course_name",
            referencedColumnName = "name"
    )
    private Course courseName;

    public SubCourse(String name, String movesThatTheComputerWillPlay, String movesThatThePlayerShouldPlay, Course courseName) {
        this.name = name;
        this.movesThatTheComputerWillPlay = movesThatTheComputerWillPlay;
        this.movesThatThePlayerShouldPlay = movesThatThePlayerShouldPlay;
        this.courseName = courseName;
    }
}
