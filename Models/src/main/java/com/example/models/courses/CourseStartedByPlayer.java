package com.example.models.courses;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity(name = "CourseStartedByPlayer")
public class CourseStartedByPlayer {
    @Id
    @SequenceGenerator(
            name = "course_started_by_player_sequence",
            sequenceName = "course_started_by_player_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_started_by_player_sequence"
    )
//    @Column(
//            name = "id",
//            updatable = false
//    )
    private Integer id;
    private Integer playerId;
    private Integer courseId;
    //    @Column(
//            name = "course_status",
//            nullable = false,
//            columnDefinition = "TEXT"
//    )
    private CourseStatus courseStatus;
}
