package com.example.models.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "Game")
@Table(
        name = "game"
)
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
    private Integer moveNumber;
    @Column(
            name = "whiteMove",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String whiteMove;
    @Column(
            name = "blackMove",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String blackMove;


    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    public Game(Integer playerId, Integer boardId, Integer moveNumber,
                String whiteMove, String blackMove, GameStatus gameStatus) {
        this.playerId = playerId;
        this.boardId = boardId;
        this.moveNumber = moveNumber;
        this.whiteMove = whiteMove;
        this.blackMove = blackMove;
        this.gameStatus = gameStatus;
    }
}
