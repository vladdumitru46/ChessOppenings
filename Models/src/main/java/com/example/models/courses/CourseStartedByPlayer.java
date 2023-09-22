package com.example.models.courses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity(name = "CourseStartedByPlayer")
@Table(name = "course_started_by_player")
@NoArgsConstructor
@Getter
@Setter
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
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;
    @Column(
            name = "player_id",
            nullable = false
    )
    private Integer playerId;
    @Column(
            name = "course_id",
            nullable = false
    )
    private Integer courseId;
    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    public CourseStartedByPlayer(Integer playerId, Integer courseId, CourseStatus courseStatus) {
        this.playerId = playerId;
        this.courseId = courseId;
        this.courseStatus = courseStatus;
    }
    
}
