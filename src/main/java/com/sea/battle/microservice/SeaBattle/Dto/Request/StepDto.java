package com.sea.battle.microservice.SeaBattle.Dto.Request;

import com.google.gson.annotations.SerializedName;

public class StepDto {
    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @SerializedName("x")
    private Integer x;
    @SerializedName("y")
    private Integer y;
}