package com.sea.battle.microservice.SeaBattle.Dto.Request;

import com.google.gson.annotations.SerializedName;

public class FindDto {
    @SerializedName("findEnemy")
    private Boolean findEnemy;

    public Boolean isFind() {
        return findEnemy;
    }
}
