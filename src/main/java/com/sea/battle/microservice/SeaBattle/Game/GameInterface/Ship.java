package com.sea.battle.microservice.SeaBattle.Game.GameInterface;

import org.springframework.stereotype.Component;

public interface Ship extends GameObject{
    int getWidth();
    int getHeight();
    boolean collision(int x, int y, int width, int height);
    ShipComponent[] getComponents();
    StateShip getState();
    enum StateShip{
        NotDamaged,
        Damaged,
        Destroy
    };
}
