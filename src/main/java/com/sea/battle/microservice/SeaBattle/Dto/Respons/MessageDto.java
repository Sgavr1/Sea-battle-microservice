package com.sea.battle.microservice.SeaBattle.Dto.Respons;

import com.google.gson.annotations.SerializedName;

public class MessageDto {
    @SerializedName("message")
    private String message;

    public MessageDto(String message) {
        this.message = message;
    }
}