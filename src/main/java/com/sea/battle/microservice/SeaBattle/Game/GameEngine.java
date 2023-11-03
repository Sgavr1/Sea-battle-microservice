package com.sea.battle.microservice.SeaBattle.Game;

import com.sea.battle.microservice.SeaBattle.Game.GameInterface.Game;
import com.sea.battle.microservice.SeaBattle.Game.GameInterface.Player;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameEngine implements Runnable{
    public List<Game> games;
    public List<Player> players;
    private Thread engine;

    public GameEngine(){
        games = new ArrayList<>();
        players = new ArrayList<>();
        engine = new Thread(this::run);
        engine.start();
    }
    @Override
    public void run() {
        while (true){
            games.stream().filter(game -> {
                if(game.update()) return true;

                Player[] ps = game.getPlayers();
                for(int i = 0; i<ps.length;i++){
                    players.remove(game.getPlayers()[i]);
                }

                return false;
            }).collect(Collectors.toList());

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
    }

    public Player getPlayerById(Long id){
        return players.stream().filter(player -> player.getId() == id).findFirst().orElse(null);
    }

    public Player getPlayerBySession(WebSocketSession session){
        return players.stream().filter(player -> player.getSession().equals(session)).findFirst().orElse(null);
    }

    public void removePlayer(Player player){
        players.remove(player);
    }
    public void addPlayer(Player player){
        players.add(player);
    }

    public void findEnemy(Player player){
        players.stream().filter(p -> p.getState()== Player.PlayerState.FindEnemy).findFirst().ifPresent(p->{
            Game game = new GameOneVsOne(p,player);
        });
    }
}
