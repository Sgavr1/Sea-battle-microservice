package com.sea.battle.microservice.SeaBattle.Game.GameInterface;

public interface Game {
    Player[] getPlayers();
    void addPlayer(Player player);
    boolean update();
    void step(int x, int y, Player player);
    GameState getState();

    enum GameState{
        Play,
        Pause,
        Finished;
    }
}
