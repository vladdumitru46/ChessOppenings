package org.example.requestClasses;


import com.example.models.courses.Player;
import org.example.interfaces.Request;

public class LogOutRequestPlayer implements Request {
    private final Player player;

    public LogOutRequestPlayer(Player boss) {
        this.player = boss;
    }

    public Player getPlayer() {
        return player;
    }
}
