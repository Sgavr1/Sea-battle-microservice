package com.sea.battle.microservice.SeaBattle.Game.GameInterface;

public interface ShipComponent extends GameObject{
    boolean getState();
    void setState(boolean value);
}
