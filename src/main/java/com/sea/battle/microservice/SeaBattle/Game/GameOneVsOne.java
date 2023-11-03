package com.sea.battle.microservice.SeaBattle.Game;

import com.google.gson.Gson;
import com.sea.battle.microservice.SeaBattle.Dto.Respons.*;
import com.sea.battle.microservice.SeaBattle.Game.GameInterface.Game;
import com.sea.battle.microservice.SeaBattle.Game.GameInterface.Player;
import com.sea.battle.microservice.SeaBattle.Game.GameInterface.Ship;

public class GameOneVsOne implements Game {
    private static final String MESSAGE_NOT_TURN  = "Now it`s the opponent`s turn";
    private static final String MESSAGE_MOVE_START  = "Start of move";
    private static final String MESSAGE_MOVE_END  = "End of move";
    private static final String MESSAGE_IMPOSSIBLE_MOVE  = "Impossible move";
    private static final String MESSAGE_MISS_MOVE  = "Miss";
    private static final String MESSAGE_DESTROY_MOVE  = "Ship is destroy";
    private static final String MESSAGE_DAMAGE_MOVE  = "Ship is damage";

    private Player[] players;
    private long timer;
    private long lastTime;
    private long timeDisconnect;
    private int playerIndex;
    private final Gson gson;

    public GameOneVsOne(){
        players = new Player[2];
        timer = 0;
        timeDisconnect = 0;
        lastTime = System.currentTimeMillis();
        playerIndex = 0;
        gson = new Gson();
    }

    public GameOneVsOne(Player player1, Player player2){
        this();
        players[0] = player1;
        players[1] = player2;

        player1.setGame(this);
        player2.setGame(this);

        player1.setState(Player.PlayerState.Play);
        player2.setState(Player.PlayerState.Play);
        player1.sendMessage(gson.toJson(new MessageDto("Start game")));
        player1.sendMessage(gson.toJson(new MessageDto("Your turn")));
        player2.sendMessage(gson.toJson(new MessageDto("Opponent`s move")));
        player1.sendMessage(gson.toJson(new PlayerInfoDto(player1.getMyArena(),player2.getArenaForEnemy())));
        player2.sendMessage(gson.toJson(new PlayerInfoDto(player2.getMyArena(),player1.getArenaForEnemy())));
    }

    @Override
    public Player[] getPlayers() {
        return players;
    }

    @Override
    public void addPlayer(Player player) {
        if(players[0] == null){
            players[0] = player;
        }else {
            players[1] = player;
        }
    }

    @Override
    public boolean update() {
        GameState state = getState();

        if(state == GameState.Finished) return false;
        if(state == GameState.Pause){
            timeDisconnect += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();

            if(timeDisconnect >= 30000){
                if(players[0].getState() == Player.PlayerState.Disconnect && players[1].getState() == Player.PlayerState.Disconnect) {

                    players[0].sendMessage(gson.toJson(new DrawDto(players[0].getId())));
                    players[1].sendMessage(gson.toJson(new DrawDto(players[1].getId())));

                } else if (players[0].getState() == Player.PlayerState.Disconnect) {

                    players[0].sendMessage(gson.toJson(new LoseDto(players[0].getId())));
                    players[1].sendMessage(gson.toJson(new VictoryDto(players[1].getId())));

                }else {

                    players[0].sendMessage(gson.toJson(new VictoryDto(players[0].getId())));
                    players[1].sendMessage(gson.toJson(new LoseDto(players[1].getId())));

                }

                players[0].setState(Player.PlayerState.End);
                players[1].setState(Player.PlayerState.End);
            }

            return true;
        }
        timeDisconnect = 0;

        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if(timer >= 10000){
            timer = 0;

            players[playerIndex].sendMessage(gson.toJson(new MessageDto(MESSAGE_MOVE_END)));

            playerIndex++;
            if(playerIndex==2){
                playerIndex = 0;
            }

            players[playerIndex].sendMessage(gson.toJson(new MessageDto(MESSAGE_MOVE_START)));
        }

        return true;
    }

    @Override
    public void step(int x, int y, Player player) {
        if(!players[0].equals(player) || !players[1].equals(player)) return;
        if(players[playerIndex].equals(player)){
            player.sendMessage(gson.toJson(new MessageDto(MESSAGE_NOT_TURN)));
            return;
        }

        Player other = players[0].equals(player) ? players[1] : players[0];
        Player.StepAnswer state = other.step(x,y);

        if(state == Player.StepAnswer.Impossible){
            player.sendMessage(gson.toJson(new MessageDto(MESSAGE_IMPOSSIBLE_MOVE)));
            return;
        }else if(state == Player.StepAnswer.Miss){
            player.sendMessage(gson.toJson(new MessageDto(MESSAGE_MISS_MOVE)));
        }else{
            player.sendMessage(gson.toJson(new PlayerInfoDto(player.getMyArena(),other.getArenaForEnemy())));
            other.sendMessage(gson.toJson(new PlayerInfoDto(other.getMyArena(),player.getArenaForEnemy())));
        }

        if(checkVictory(other)){
            player.sendMessage(gson.toJson(new VictoryDto(player.getId())));
            other.sendMessage(gson.toJson(new LoseDto(other.getId())));

            return;
        }

        lastTime = System.currentTimeMillis();
        playerIndex++;
        if(playerIndex == 2){
            playerIndex = 0;
        }
    }

    @Override
    public GameState getState() {
        if(players[0].getState() == Player.PlayerState.End || players[1].getState() == Player.PlayerState.End) return GameState.Finished;
        if (players[0].getState() != Player.PlayerState.Play || players[1].getState() != Player.PlayerState.Play) return GameState.Pause;
        return GameState.Play;
    }

    public boolean checkVictory(Player player){
        for (Ship ship:player.getShips()) {
            if(ship.getState() != Ship.StateShip.Destroy){
                return false;
            }
        }

        return true;
    }
}
