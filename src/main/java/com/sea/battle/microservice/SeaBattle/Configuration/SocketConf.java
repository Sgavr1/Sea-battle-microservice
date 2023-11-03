package com.sea.battle.microservice.SeaBattle.Configuration;

import com.sea.battle.microservice.SeaBattle.Game.GameEngine;
import com.sea.battle.microservice.SeaBattle.SokecketController.SocketGameController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class SocketConf implements WebSocketConfigurer {
    @Autowired
    private GameEngine gameEngine;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketGameController(gameEngine),"/Battle").setAllowedOrigins("*");
    }
}
