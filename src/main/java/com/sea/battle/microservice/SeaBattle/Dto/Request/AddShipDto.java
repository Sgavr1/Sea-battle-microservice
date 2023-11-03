package com.sea.battle.microservice.SeaBattle.Dto.Request;

import com.google.gson.annotations.SerializedName;

public class AddShipDto {
    @SerializedName("posX")
    private Integer x;
    @SerializedName("posY")
    private Integer y;
    @SerializedName("width")
    private Integer width;
    @SerializedName("height")
    private Integer height;

    public Integer getY() {
        return y;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
    public Integer getX() {
        return x;
    }
}
