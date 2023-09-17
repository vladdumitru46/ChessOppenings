package org.example.repositoryes.interfaces;

import com.example.models.courses.Player;

public interface IRepoPlayer extends ICrudRepository<Integer, Player> {
    Player findByEmailAndPassword(String email, String password);
}
