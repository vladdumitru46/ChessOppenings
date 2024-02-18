package com.example.models.courses;

import com.example.models.board.Board;
import com.example.models.player.Player;
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
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "player_id"
    )
    private Player playerId;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "course_name",
            referencedColumnName = "name"
    )
    private Course courseName;


    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "board_id"
    )
    private Board boardId;


    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    private Integer moveNumber = 1;

    public CourseStartedByPlayer(Player playerId, Course courseName, Board boardId, CourseStatus courseStatus) {
        this.playerId = playerId;
        this.courseName = courseName;
        this.boardId = boardId;
        this.courseStatus = courseStatus;
    }

    public CourseStartedByPlayer(Player playerId, Course courseName) {
        this.playerId = playerId;
        this.courseName = courseName;
    }

    public CourseStartedByPlayer(Player playerId, Course courseName, Board boardId) {
        this.playerId = playerId;
        this.courseName = courseName;
        this.boardId = boardId;
    }
}
