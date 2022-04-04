package com.example.yapdev.api;

import com.example.yapdev.repository.dto.resUser;
import com.example.yapdev.repository.dto.user;
import com.example.yapdev.service.userService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class userController {

    private final userService userService;


    @PostMapping("/signin")
    public String signIn(@RequestBody user user){


        String name = user.getName();

        log.info("@controller@.signIn      signIn request inbound,,,  name : " + name);

        String resCode = userService.signIn(user);

        log.info("@controller@.signIn      send resCode to app,,,  resCode : " + resCode);

        return resCode;
    }

    @PostMapping("/login")
    public resUser login(@RequestBody user user, HttpSession session){

        String name = user.getName();

        log.info("@controller@.login      login request income,,,  name : " + name);

        String resCode = userService.login(user);
        System.out.println(resCode);

        if (resCode.equals("login200")){
            user u = userService.returnUser(user.getUserId());
            resUser resUser = new resUser();

            resUser.setUserId(u.getUserId());
            resUser.setName(u.getName());
            resUser.setPw(u.getPw());
            resUser.setAdmin(u.isAdmin());
            resUser.setResCode(resCode);

            session.setAttribute("user", resUser.getUserId());




            log.info("@controller@.login    " + resUser.getName() + "successfully login,,, resCode : " + resCode);
            log.info("@controller@.login    send resUser to app,,,");
            return resUser;
        }

        log.info("@controller@.login    login failed,,, resCode : " + resCode);

        resUser resUser = new resUser();

        resUser.setUserId(user.getUserId());
        resUser.setPw(user.getPw());
        resUser.setResCode(resCode);

        log.info("@controller@.login    send resUser to app,,, ");
        return resUser;
    }


    @GetMapping("/checkSession")
    public resUser test(HttpSession session){
        log.info("checking session,,,,  userId is returned,,," + session.getAttribute("user"));

        user u = userService.returnUser(session.getAttribute("user").toString());
        resUser resUser = new resUser();

        resUser.setUserId(u.getUserId());
        resUser.setName(u.getName());
        resUser.setPw(u.getPw());
        resUser.setAdmin(u.isAdmin());
        resUser.setResCode(null);

        return resUser;
    }
}
