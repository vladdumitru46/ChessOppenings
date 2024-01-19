package org.example.requestClasses;


import com.example.models.courses.Player;
import org.example.interfaces.Request;

public class LogInRequestPlayer implements Request {
    private final Player player;

    public LogInRequestPlayer(Player boss) {
        this.player = boss;
    }

    public Player getPlayer() {
        return player;
    }
}
