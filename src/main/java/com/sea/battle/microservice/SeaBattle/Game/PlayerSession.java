package com.sea.battle.microservice.SeaBattle.Game;

import com.sea.battle.microservice.SeaBattle.Game.GameInterface.Player;
import org.springframework.web.socket.WebSocketSession;

public class PlayerSession {
    private WebSocketSession session;
    private Player player;

    public PlayerSession(WebSocketSession session, Player player){
        this.session =session;
        this.player = player;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
