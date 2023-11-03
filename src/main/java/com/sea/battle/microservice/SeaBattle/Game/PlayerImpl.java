package com.sea.battle.microservice.SeaBattle.Game;

import com.sea.battle.microservice.SeaBattle.Game.GameInterface.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerImpl implements Player {
    private Long id;
    private WebSocketSession session;
    private Game game;
    private List<Ship> ships;
    private List<GameObject> marks;
    private PlayerState state;

    public PlayerImpl(Long id, WebSocketSession session) {
        state = PlayerState.NewPlayer;
        this.id = id;
        this.session = session;

        ships = new ArrayList<>(15);
        marks = new ArrayList<>();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public WebSocketSession getSession() {
        return session;
    }

    @Override
    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public boolean addShip(Ship ship) {
        if(ship.getX() < 0 || ship.getY() < 0 || ship.getX() > 9 || ship.getY() > 9 || ship.getWidth() < 1 || ship.getWidth() > 5 || ship.getHeight() < 1 || ship.getHeight() > 5){
            return false;
        }

        int count5 = 0, count4 = 0, count3 = 0, count2 = 0, count1 = 0;

        for(Ship s:ships){
            if(s.getWidth() == 5 || s.getHeight() == 5){
                count5++;
            }else if(s.getWidth() == 4 || s.getHeight() == 4) {
                count4++;
            }else if(s.getWidth() == 3 || s.getHeight() == 3){
                count3++;
            }else if(s.getWidth() == 2 || s.getHeight() == 2){
                count2++;
            }else {
                count1++;
            }
        }

        if((ship.getWidth() == 5 || ship.getHeight()==5) && count5 > 0){
            return false;
        }else  if((ship.getWidth() == 4 || ship.getHeight() == 4) && count4 > 1){
            return false;
        }else if((ship.getWidth() == 3 || ship.getHeight() == 3) && count3 > 2){
            return false;
        }else if((ship.getWidth() == 2 || ship.getHeight() == 2) && count2 > 3){
            return false;
        }else if(count1 > 4){
            return false;
        }

        for (Ship s:ships){
            if(s.collision(ship.getX(),ship.getY(),ship.getWidth(),ship.getHeight())){
                return false;
            }
        }
        ships.add(ship);
        return true;
    }

    @Override
    public boolean checkShip() {
        int count5 = 0, count4 = 0, count3 = 0, count2 = 0, count1 = 0;

        for(Ship s:ships){
            if(s.getWidth() == 5 || s.getHeight() == 5){
                count5++;
            }else if(s.getWidth() == 4 || s.getHeight() == 4) {
                count4++;
            }else if(s.getWidth() == 3 || s.getHeight() == 3){
                count3++;
            }else if(s.getWidth() == 2 || s.getHeight() == 2){
                count2++;
            }else {
                count1++;
            }
        }
        if(count5 == 1 && count4 == 2 && count3 == 3 && count2 == 4 && count1 == 5) return true;
        return false;
    }

    @Override
    public void clearShip() {
        ships = new ArrayList<>(15);
    }

    @Override
    public Ship[] getShips() {
        return (Ship[]) ships.toArray();
    }

    @Override
    public Integer[][] getMyArena() {
        Integer[][] arena = new Integer[10][10];

        for (int i = 0; i<10; i++){
            for(int j = 0; j<10;j++){
                arena[i][j] = 0;
            }
        }

        for(Ship ship:ships){
            for(ShipComponent component:ship.getComponents()){
                if(ship.getState() == Ship.StateShip.Destroy){
                    arena[component.getX()][component.getY()] = -3;
                    continue;
                }
                if(component.getState()){
                    arena[component.getX()][component.getY()] = ship.getX() >= ship.getY() ? ship.getX() : ship.getY();
                }else {
                    arena[component.getX()][component.getY()] = -2;
                }
            }
        }

        for (GameObject mark:marks){
            arena[mark.getX()][mark.getY()] = -1;
        }

        return arena;
    }

    @Override
    public Integer[][] getArenaForEnemy() {
        Integer[][] arena = new Integer[10][10];

        for (int i = 0; i<10; i++){
            for(int j = 0; j<10;j++){
                arena[i][j] = 0;
            }
        }

        for(Ship ship:ships){
            for(ShipComponent component:ship.getComponents()){
                if(ship.getState() == Ship.StateShip.Destroy){
                    arena[component.getX()][component.getY()] = -3;
                    continue;
                }
                if(component.getState()){
                    arena[component.getX()][component.getY()] = 0;
                }else {
                    arena[component.getX()][component.getY()] = -2;
                }
            }
        }

        for (GameObject mark:marks){
            arena[mark.getX()][mark.getY()] = -1;
        }

        return arena;
    }

    @Override
    public void sendMessage(String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {}
    }

    @Override
    public StepAnswer step(int x, int y) {
        for (Ship ship:ships){
            for (ShipComponent component:ship.getComponents()) {
                if(component.getX() == x && component.getY() == y){
                    if(component.getState()){
                        component.setState(false);
                        if(ship.getState() == Ship.StateShip.Damaged){
                            return StepAnswer.Damage;
                        } else if (ship.getState() == Ship.StateShip.Destroy) {
                            return StepAnswer.Destroy;
                        }
                    }
                    else {
                        return StepAnswer.Impossible;
                    }
                }
            }
        }
        for (GameObject mark:marks){
            if(mark.getX() == x && mark.getY() == y){
                return StepAnswer.Impossible;
            }
        }

        marks.add(new GameObject() {
            @Override
            public int getX() {
                return x;
            }

            @Override
            public int getY() {
                return y;
            }
        });

        return StepAnswer.Miss;
    }

    @Override
    public PlayerState getState() {
        return state;
    }

    @Override
    public void setState(PlayerState playerState) {
        state = playerState;
    }
}