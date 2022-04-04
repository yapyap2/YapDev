package com.example.yapdev.api;

import com.example.yapdev.repository.dto.testDto;
import com.example.yapdev.threadTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class testController {

    int num = 0;
    List<threadTest> list = new ArrayList<>();

    @PostMapping("/test")
    public String test(@RequestBody testDto dto){
        log.info("Tested,,,,,, " + dto.getName());
        System.out.println(dto.getList());
        return dto.getName();
    }

    @PostMapping("/test2")
    public void test2(@RequestBody String str){
        System.out.println(str);
    }

    @GetMapping("/threadtest")
    public String threadTest(){
        threadTest thread = new threadTest();
        thread.setNum(num++)    ;
        thread.start();
        list.add(thread);
        return "tested....";
    }

    @GetMapping("/threadstop")
    public void threadStop(@RequestParam int i){
        list.get(i).setStartSwitch(false);
        list.remove(i);
    }
}
