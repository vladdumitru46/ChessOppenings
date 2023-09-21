package com.example.models.courses;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity(name = "Course")
@Table(name = "course")
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
    private Integer boardId;
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


    public Course(String name, String description, Integer board, String movesThatTheComputerWillPlay, String movesThatThePlayerShouldPlay) {
        this.name = name;
        this.description = description;
        this.boardId = board;
        this.movesThatTheComputerWillPlay = movesThatTheComputerWillPlay;
        this.movesThatThePlayerShouldPlay = movesThatThePlayerShouldPlay;
    }

    public Course() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBoardId() {
        return boardId;
    }

    //TODO setBoard
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public String getMovesThatTheComputerWillPlay() {
        return movesThatTheComputerWillPlay;
    }

    public void setMovesThatTheComputerWillPlay(String movesThatTheComputerWillPlay) {
        this.movesThatTheComputerWillPlay = movesThatTheComputerWillPlay;
    }

    public String getMovesThatThePlayerShouldPlay() {
        return movesThatThePlayerShouldPlay;
    }

    public void setMovesThatThePlayerShouldPlay(String movesThatThePlayerShouldPlay) {
        this.movesThatThePlayerShouldPlay = movesThatThePlayerShouldPlay;
    }

}
