package com.donatoordep.anime_list_api.entities;

import jakarta.validation.constraints.Size;

public class Code {

    @Size(min = 64, max = 64, message = "minimum lenght is 64 caracters")
    private String code;

    public Code() {
    }

    public Code(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}