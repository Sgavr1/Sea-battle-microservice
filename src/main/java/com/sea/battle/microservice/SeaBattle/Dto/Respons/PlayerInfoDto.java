package com.sea.battle.microservice.SeaBattle.Dto.Respons;

public class PlayerInfoDto {
    private Integer[][] myArena;
    private Integer[][] enemyArena;

    public PlayerInfoDto(Integer[][] myArena, Integer[][] enemyArena) {
        this.myArena = myArena;
        this.enemyArena = enemyArena;
    }
}
