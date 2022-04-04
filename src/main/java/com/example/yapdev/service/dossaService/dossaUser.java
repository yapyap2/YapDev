package com.example.yapdev.service.dossaService;

import lombok.Data;

import java.util.List;

@Data
public class dossaUser {

    public dossaUser(String name) {
        this.name = name;
    }

    private String name;
    private List<String> keyword;

    private dossaService dossaService;
}
