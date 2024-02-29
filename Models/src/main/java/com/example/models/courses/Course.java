package com.example.models.courses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity(name = "Course")
@Table(name = "course")
@NoArgsConstructor
@Getter
@Setter
public class Course implements Serializable {
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
    private Long id;
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
            name = "video",
            nullable = false
    )
    private String video;


    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
