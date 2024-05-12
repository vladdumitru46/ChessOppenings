package com.example.models.game;

import lombok.*;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "Game")
@Table(
        name = "game"
)
@ToString
public class Game {
    @Id
    @SequenceGenerator(
            name = "game_sequence",
            sequenceName = "game_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "game_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "playerId",
            nullable = false
    )
    private Integer playerId;

    @Column(
            name = "boardId",
            nullable = false
    )
    private Integer boardId;

    @Column(
            name = "moveNumber",
            nullable = false
    )
    private Integer moveNumber = 1;
    @Column(
            name = "whiteMove",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String whiteMove = "";

    @Column(
            name = "blackMove",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String blackMove = "";


    @Column(
            name = "whites_turn",
            nullable = false
    )
    private boolean whitesTurn = true;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @Column(
            name = "moves",
            columnDefinition = "TEXT",
            length = 2048

    )
    private String moves = "";

    public Game(Integer playerId, Integer boardId, GameStatus gameStatus) {
        this.playerId = playerId;
        this.boardId = boardId;
        this.gameStatus = gameStatus;
    }
}
