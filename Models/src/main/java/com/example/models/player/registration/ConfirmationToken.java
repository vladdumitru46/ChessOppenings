package com.example.models.player.registration;

import com.example.models.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ConfirmationToken")
@Table(
        name = "confirmationToken"
)
public class ConfirmationToken {
    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
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
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "player_id"
    )
    private Player player;

    public ConfirmationToken(String token, LocalDateTime cratedAt, LocalDateTime expiresAt, Player player) {
        this.token = token;
        this.cratedAt = cratedAt;
        this.expiresAt = expiresAt;
        this.player = player;
    }
}
