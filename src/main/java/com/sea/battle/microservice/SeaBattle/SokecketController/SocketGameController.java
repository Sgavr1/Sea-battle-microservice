package com.sea.battle.microservice.SeaBattle.SokecketController;

import com.google.gson.Gson;
import com.sea.battle.microservice.SeaBattle.Game.GameEngine;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SocketGameController extends TextWebSocketHandler {
    private final GameEngine gameEngine;
    private Gson gson;
    public SocketGameController(GameEngine gameEngine){
        this.gameEngine = gameEngine;
        gson = new Gson();
    }

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    }
}
