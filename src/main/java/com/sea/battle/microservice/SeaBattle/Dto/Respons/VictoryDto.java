package com.sea.battle.microservice.SeaBattle.Dto.Respons;

import com.google.gson.annotations.SerializedName;

public class VictoryDto {
    @SerializedName("playerVictoryId")
    private Long id;

    public VictoryDto(Long id) {
        this.id = id;
    }
}
