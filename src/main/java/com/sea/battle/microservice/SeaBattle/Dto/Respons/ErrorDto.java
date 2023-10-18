package com.sea.battle.microservice.SeaBattle.Dto.Respons;

import com.google.gson.annotations.SerializedName;

public class ErrorDto {
    private int status;
    @SerializedName("errorMessage")
    private String message;

    public ErrorDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
