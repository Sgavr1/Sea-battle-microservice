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
}
