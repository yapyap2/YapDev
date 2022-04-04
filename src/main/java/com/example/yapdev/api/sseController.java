package com.example.yapdev.api;

import com.example.yapdev.repository.dto.keywordDto;
import com.example.yapdev.service.sseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sse")
public class sseController {

    private final sseService sseService;
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    ArrayList<String> keywords = new ArrayList<>();
    String userId;
    keywordDto keywordDto;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(HttpSession session){

        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        sseService.createThread(userId, emitter, keywords);


        System.out.println("emitter send");
        return emitter;
    }

    @GetMapping("/subscribestop")
    public String subscribeStop(){
        sseService.stopThread(userId);

        return "thread successfully stopped";
    }

    @GetMapping("/ssetest")
    public SseEmitter subscribeTest(){


        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        String userId = "test";

        sseService.createThread(userId, emitter, keywords);

        System.out.println("test.....");

        return emitter;
    }

    @PostMapping(value = "/keywordset")
    public void setKeyword(@RequestBody String listStr, HttpSession session){

        userId = session.getAttribute("user").toString();

        ObjectMapper mapper = new ObjectMapper();
        try {
           keywordDto =  mapper.readValue(listStr, keywordDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        keywords = keywordDto.getKeyword();

        System.out.println(keywords);

    }

    @PostMapping("/keywordsettest")
    public String setKeywordTest(HttpSession session){


        userId = session.getAttribute("user").toString();

        keywords.add("자이언트");
        keywords.add("메리다");

        System.out.println(keywords);

        return "ok";
    }


    @GetMapping("/subscribestoptest")
    public String subscribeStopTest(){
        sseService.stopThread("test");

        return "thread successfully stopped";
    }

    @PostMapping("/sessionset")
    public void setSession(HttpSession session){
        userId = session.getAttribute("user").toString();
    }
}
