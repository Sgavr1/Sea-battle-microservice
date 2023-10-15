package com.sea.battle.microservice.SeaBattle.Game;

import org.springframework.stereotype.Component;

@Component
public class GameEngine implements Runnable{
    private Thread engine;

    public GameEngine(){
        engine = new Thread(this::run);
        engine.start();
    }
    @Override
    public void run() {
        while (true){

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
    }
}
