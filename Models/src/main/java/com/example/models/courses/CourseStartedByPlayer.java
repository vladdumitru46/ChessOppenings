package com.example.models.courses;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity(name = "CourseStartedByPlayer")
@Table(name = "course_started_by_player")
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
    @Column(
            name = "course_status",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private CourseStatus courseStatus;

    public CourseStartedByPlayer(Integer playerId, Integer courseId, CourseStatus courseStatus) {
        this.playerId = playerId;
        this.courseId = courseId;
        this.courseStatus = courseStatus;
    }

    public CourseStartedByPlayer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }
}
