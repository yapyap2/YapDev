package com.example.yapdev.repository.dto;

import lombok.Data;

@Data
public class resUser{
    String name;
    String userId;
    String pw;
    boolean admin;
    String resCode;


    public void setResCode(String code){
        this.resCode = code;
    }
}
