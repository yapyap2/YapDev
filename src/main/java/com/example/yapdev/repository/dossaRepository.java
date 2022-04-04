package com.example.yapdev.repository;

import com.example.yapdev.service.dossaService.dossaService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;

@Component
public class dossaRepository  {

    HashMap<String, dossaService> map = new HashMap<>();

    public void save(String id, dossaService service){
        map.put(id, service);
    }
    public void delete(String id){
        map.remove(id);
    }
    public dossaService get(String id){
        return map.get(id);
    }


}
