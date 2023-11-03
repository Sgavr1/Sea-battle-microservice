package com.sea.battle.microservice.SeaBattle.Game;

import com.sea.battle.microservice.SeaBattle.Game.GameInterface.ShipComponent;

public class ComponentImp implements ShipComponent {
    private int x;
    private int y;
    private boolean state;

    public ComponentImp(int x, int y){
        this.x = x;
        this.y = y;
        state = true;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public boolean getState() {
        return state;
    }

    @Override
    public void setState(boolean value) {
        state = value;
    }
}
