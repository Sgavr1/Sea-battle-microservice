package com.sea.battle.microservice.SeaBattle.Dto.Respons;

import com.google.gson.annotations.SerializedName;

public class DrawDto {
    @SerializedName("playerDrawId")
    private Long id;

    public DrawDto(Long id) {
        this.id = id;
    }
}
