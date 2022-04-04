package com.example.yapdev.service;

import com.example.yapdev.repository.dossaRepository;
import com.example.yapdev.service.dossaService.dossaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class sseService {

    private final dossaRepository dossaRepository;


    public void createThread(String id, SseEmitter emitter, List<String> keywords){
        dossaService service = new dossaService();

        dossaRepository.save(id, service);
        service.startSse(keywords, emitter);




        System.out.println("dossa service successfully started");
    }

    public void stopThread(String id){
        dossaRepository.get(id).stopThread();
        dossaRepository.delete(id);
    }


}

