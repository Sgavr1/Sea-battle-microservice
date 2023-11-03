package com.sea.battle.microservice.SeaBattle.Game;

import com.sea.battle.microservice.SeaBattle.Game.GameInterface.Ship;
import com.sea.battle.microservice.SeaBattle.Game.GameInterface.ShipComponent;

public class ShipImp implements Ship {
    private int x;
    private int y;
    private int width;
    private int height;
    private ShipComponent[] components;

    public ShipImp(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean collision(int x, int y, int width, int height) {
        if(this.x - 1 <= x + width-1 && this.x + this.width >= x){
            if(this.y -1 <= y + height-1 && this.y + this.height >= y){
                return true;
            }
        }

        return false;
    }

    @Override
    public ShipComponent[] getComponents() {
        return components;
    }

    @Override
    public StateShip getState() {
        boolean status = true;
        boolean deadStatus = true;
        for (ShipComponent component:components){
            if(component.getState()){
                deadStatus = false;
            }
            else {
                status = false;
            }
        }

        if(deadStatus) return StateShip.Destroy;
        return status ? StateShip.NotDamaged : StateShip.Damaged;
    }
}
