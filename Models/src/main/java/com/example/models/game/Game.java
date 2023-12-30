package com.example.models.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
            name = "whitePlayerId",
            nullable = false
    )
    private Integer whitePlayerId;

    @Column(
            name = "blackPlayerId",
            nullable = false
    )
    private Integer blackPlayerId;

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

    public Game(Integer whitePlayerId, Integer blackPlayerId, Integer moveNumber, String whiteMove, String blackMove) {
        this.whitePlayerId = whitePlayerId;
        this.blackPlayerId = blackPlayerId;
        this.moveNumber = moveNumber;
        this.whiteMove = whiteMove;
        this.blackMove = blackMove;
    }
}
