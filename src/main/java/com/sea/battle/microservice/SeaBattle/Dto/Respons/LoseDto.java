package com.sea.battle.microservice.SeaBattle.Dto.Respons;

import com.google.gson.annotations.SerializedName;

public class LoseDto {
    @SerializedName("playerLoseId")
    private Long id;

    public LoseDto(Long id) {
        this.id = id;
    }
}
