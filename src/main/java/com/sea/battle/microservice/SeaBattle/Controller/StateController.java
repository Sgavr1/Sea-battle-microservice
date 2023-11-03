package com.sea.battle.microservice.SeaBattle.Controller;

import com.sea.battle.microservice.SeaBattle.Game.GameEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateController {
    @Autowired
    private GameEngine gameEngine;

    @GetMapping("/")
    public String getState(){
        return "Run";
    }
}
