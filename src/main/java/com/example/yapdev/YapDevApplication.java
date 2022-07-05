package com.example.yapdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YapDevApplication {

    public static void main(String[] args) {


        System.setProperty("http.proxyHost", "139.162.72.52");
        System.setProperty("http.proxyPort", "80");
        SpringApplication.run(YapDevApplication.class, args);
    }

}
