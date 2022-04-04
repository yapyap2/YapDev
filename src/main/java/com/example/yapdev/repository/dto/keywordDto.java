package com.example.yapdev.repository.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class keywordDto {

    ArrayList<String> keyword;

    public ArrayList<String> getKeyword() {
        return keyword;
    }

    public void setKeyword(ArrayList<String> keyword) {
        this.keyword = keyword;
    }
}
