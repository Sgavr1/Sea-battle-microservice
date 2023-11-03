package com.sea.battle.microservice.SeaBattle.SokecketController;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sea.battle.microservice.SeaBattle.Dto.Request.AddShipDto;
import com.sea.battle.microservice.SeaBattle.Dto.Request.FindDto;
import com.sea.battle.microservice.SeaBattle.Dto.Request.RegistrationDto;
import com.sea.battle.microservice.SeaBattle.Dto.Request.StepDto;
import com.sea.battle.microservice.SeaBattle.Dto.Respons.MessageDto;
import com.sea.battle.microservice.SeaBattle.Dto.Respons.PlayerArenaDto;
import com.sea.battle.microservice.SeaBattle.Game.GameEngine;
import com.sea.battle.microservice.SeaBattle.Game.GameInterface.Player;
import com.sea.battle.microservice.SeaBattle.Game.PlayerImpl;
import com.sea.battle.microservice.SeaBattle.Game.ShipImp;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SocketGameController extends TextWebSocketHandler {
    private final GameEngine gameEngine;
    private Gson gson;
    public SocketGameController(GameEngine gameEngine){
        this.gameEngine = gameEngine;
        gson = new Gson();
    }

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        try{
            RegistrationDto registrationDto = gson.fromJson(message.getPayload(), RegistrationDto.class);
            if(registrationDto.getId() != null){
                Player player = gameEngine.getPlayerBySession(session);
                if(player == null){
                    player = gameEngine.getPlayerById(registrationDto.getId());
                    if(player == null){
                        player = new PlayerImpl(registrationDto.getId(), session);
                        gameEngine.addPlayer(player);
                    }
                    else {
                        player.setSession(session);
                        player.setState(Player.PlayerState.Play);
                    }
                }
            }
            else {
                session.sendMessage(new TextMessage(gson.toJson(new MessageDto("You are already registered"))));
                return;
            }
        }catch (JsonSyntaxException e){

        }

        try{
            AddShipDto addShipDto = gson.fromJson(message.getPayload(), AddShipDto.class);
            if(addShipDto.getX() != null && addShipDto.getY() != null && addShipDto.getWidth() != null && addShipDto.getHeight() != null){
                Player player = gameEngine.getPlayerBySession(session);
                if(player != null){
                    if(player.addShip(new ShipImp(addShipDto.getX(), addShipDto.getY(), addShipDto.getWidth(), addShipDto.getHeight()))){
                        player.sendMessage(gson.toJson(new PlayerArenaDto(player.getMyArena())));
                    }
                    else {
                        session.sendMessage(new TextMessage(gson.toJson(new MessageDto("Bad ship position"))));
                    }
                }
                else {
                    session.sendMessage(new TextMessage(gson.toJson(new MessageDto("You are not registered"))));
                    return;
                }
            }
        }catch (JsonSyntaxException e){

        }

        try{
            AddShipDto[] addShipDtos = gson.fromJson(message.getPayload(), AddShipDto[].class);
            if(addShipDtos != null){
                Player player = gameEngine.getPlayerBySession(session);
                if(player != null){
                    for(AddShipDto addShipDto:addShipDtos){
                        if(player.addShip(new ShipImp(addShipDto.getX(), addShipDto.getY(), addShipDto.getWidth(), addShipDto.getHeight()))){

                        }
                        else {
                            session.sendMessage(new TextMessage(gson.toJson(new MessageDto("Bad ship position"))));
                            return;
                        }
                    }
                    player.sendMessage(gson.toJson(new PlayerArenaDto(player.getMyArena())));
                }
                else {
                    session.sendMessage(new TextMessage(gson.toJson(new MessageDto("You are not registered"))));
                    return;
                }
            }
        }catch (JsonSyntaxException e){

        }

        try{
            FindDto findDto = gson.fromJson(message.getPayload(), FindDto.class);
            if(findDto.isFind() != null){
                Player player = gameEngine.getPlayerBySession(session);
                if(player != null){
                    if(player.checkShip()){
                        player.setState(Player.PlayerState.FindEnemy);
                        gameEngine.findEnemy(player);
                    }
                }
            }
        }catch (JsonSyntaxException e){

        }

        try{
            StepDto stepDto = gson.fromJson(message.getPayload(), StepDto.class);
            if(stepDto.getX() != null && stepDto.getY() != null){
                Player player = gameEngine.getPlayerBySession(session);
                if(player != null){
                    player.getGame().step(stepDto.getX(), stepDto.getY(), player);
                }
            }
        }catch (JsonSyntaxException e){

        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Player player = gameEngine.getPlayerBySession(session);
        if(player != null){
            Player.PlayerState state = player.getState();
            if(state == Player.PlayerState.Play){
                player.setState(Player.PlayerState.Disconnect);
                return;
            }else {
                gameEngine.removePlayer(player);
            }
        }
    }
}
