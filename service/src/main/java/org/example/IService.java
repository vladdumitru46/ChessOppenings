package org.example;

import com.example.models.courses.Player;

public interface IService {
    void logIn(Player player, IServiceObserver client) throws Exception;

    void logOut(Player player, IServiceObserver client) throws Exception;

}
