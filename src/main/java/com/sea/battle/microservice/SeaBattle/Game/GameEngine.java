package com.sea.battle.microservice.SeaBattle.Game;

import com.sea.battle.microservice.SeaBattle.Game.GameInterface.Game;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameEngine implements Runnable{
    public List<Game> games;
    public List<PlayerSession> players;
    private Thread engine;

    public GameEngine(){
        games = new ArrayList<>();
        engine = new Thread(this::run);
        engine.start();
    }
    @Override
    public void run() {
        while (true){
            games.stream().filter(game -> game.update()).collect(Collectors.toList());

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
    }
}
