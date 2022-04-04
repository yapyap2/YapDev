package com.example.yapdev.service.dossaService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class dossaSocketHandler extends TextWebSocketHandler {

    private HashMap<String, dossaUser> userList  = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String sessionId = session.getId();

        dossaUser user = new dossaUser(session.getAttributes().get("name").toString());

        //user.setDossaService(new dossaService());

        userList.put("sessionId", user);

        log.info("@websocket@       " + session.getAttributes().get("name") + "is joined socket");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        String sessionId = session.getId();

        String type = payload.split(",")[0];
        String content = payload.split(",")[1];

        if (type.equals("control")){
            if (content.equals("start")){
                dossaUser user = userList.get(sessionId);
                dossaService service = user.getDossaService();


            }


        }



    }
}
