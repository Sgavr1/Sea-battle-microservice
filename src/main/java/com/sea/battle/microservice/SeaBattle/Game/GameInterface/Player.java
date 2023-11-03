package com.sea.battle.microservice.SeaBattle.Game.GameInterface;

import org.springframework.web.socket.WebSocketSession;

public interface Player {
    Long getId();
    WebSocketSession getSession();
    void setSession(WebSocketSession session);
    Game getGame();
    void setGame(Game game);
    boolean addShip(Ship ship);
    boolean checkShip();
    void clearShip();
    Ship[] getShips();
    Integer[][] getMyArena();
    Integer[][] getArenaForEnemy();
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
