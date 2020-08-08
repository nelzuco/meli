package com.meli.iplookup.dto;

public class ErrorResponseDTO {
    public Integer code;
    public String message;

    public ErrorResponseDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
