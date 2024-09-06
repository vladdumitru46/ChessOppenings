package com.example.models.player;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "PlayerSession")
@Table(name = "player_sessions")
public class PlayerSession {
    @Id
    @SequenceGenerator(
            name = "player_session_sequence",
            sequenceName = "player_session_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "player_session_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "player_id",
            referencedColumnName = "id"
    )
    private Player player;

    @Column(
            name = "token",
            nullable = false
    )
    private String token;

    @Column(
            name = "cratedAt",
            nullable = false
    )
    private LocalDateTime cratedAt;
    @Column(
            name = "expiresAt",
            nullable = false
    )
    private LocalDateTime expiresAt;

    public PlayerSession(Player player, String token, LocalDateTime cratedAt, LocalDateTime expiresAt) {
        this.player = player;
        this.token = token;
        this.cratedAt = cratedAt;
        this.expiresAt = expiresAt;
    }
}
