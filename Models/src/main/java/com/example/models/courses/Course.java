package com.example.models.courses;

import com.example.models.board.Board;

public class Course extends Entity<Integer> {
    private String name;
    private String description;
    private Board board;
    private String movesThatTheComputerWillPlay;
    private String movesThatThePlayerShouldPlay;
    private Integer playerId;
    private CourseStatus courseStatus;

    public Course(String name, String description, Board board, String movesThatTheComputerWillPlay, String movesThatThePlayerShouldPlay, Integer playerId, CourseStatus courseStatus) {
        this.name = name;
        this.description = description;
        this.board = board;
        this.movesThatTheComputerWillPlay = movesThatTheComputerWillPlay;
        this.movesThatThePlayerShouldPlay = movesThatThePlayerShouldPlay;
        this.playerId = playerId;
        this.courseStatus = courseStatus;
    }

    public Course() {
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

    public Board getBoard() {
        return board;
    }

    //TODO setBoard
    public void setBoard(Board board) {
        this.board = board;
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

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }
}
