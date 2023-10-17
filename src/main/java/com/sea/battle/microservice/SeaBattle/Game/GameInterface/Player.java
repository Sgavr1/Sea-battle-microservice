package com.sea.battle.microservice.SeaBattle.Game.GameInterface;

public interface Player {
    Long getId();
    Game getGame();
    void setGame(Game game);
    boolean addShip(Ship ship);
    void clearShip();
    Ship[] getShips();
    Integer[] getMyArena();
    Integer[] getArenaForEnemy();
    void sendMessage(String message);
    StepAnswer step(int x, int y);
    PlayerState getState();
    void setState(PlayerState playerState);
    enum PlayerState{
        NewPlayer,
        Ready,
        FindEnemy,
        Play,
        Disconnect,
        End
    }
    enum StepAnswer{
        Impossible,
        Miss,
        Damage,
        Destroy
    }
}
