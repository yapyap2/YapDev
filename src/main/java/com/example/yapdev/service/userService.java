package com.example.yapdev.service;

import com.example.yapdev.repository.dto.user;
import com.example.yapdev.repository.userRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class userService{

    private final userRepository userRepository;

    private final int adminCode = 17120206;

    public String signIn(user user){

        if (checkId(user.getUserId())) {
            log.info("@userService@     ID overLap or char less 4");
            return "signIn401";   //ID overLap or char less 4
        }

        if (checkPw(user.getPw())){
            log.info("@userService@.signIn     pw char less 6");
            return "signIn402";   //pw char less 6
        }

        if (user.getName().length() <= 0 ){
            log.info("@userService@.signIn     name char less 3");
            return "signIn403";  //name char less 3
        }

        if (user.getCode() == adminCode){
            log.info("@userService@.signIn      adminCode is match");
            user.setAdmin(true);    //give adminPermission
        }

        userRepository.save(user);
        log.info("@userService@.signIn      user successfully saved to repository,,,");
        return "signIn200";    //all Ok
    }

    public String login(user user){

        user foundedUser = userRepository.findByUserId(user.getUserId());

        if(foundedUser == null){
            log.info("@userService@.login     id doesn't exist");
            return "login401"; //id is not exist
        }

        if (!foundedUser.getPw().equals(user.getPw())){
            log.info("@userService@.login     pw is not match");
            return "login402"; //pw is not match
        }

        log.info("@userService@.login     successfully login,,,");
        return "login200";
    }

    public user returnUser(String id){
        return userRepository.findByUserId(id);
    }

    private boolean checkId(String id){
        if (id.length() < 4){
            return true;
        }

        user user = userRepository.findByUserId(id);

        if(user != null){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean checkPw(String pw){
        if (pw.length() < 6){
            return true;
        }
        return false;
    }

    public String findId(String name){
        user targetUser = userRepository.findByName(name);

        if (targetUser == null){
            return "error cant find user";
        }

        return targetUser.getUserId();
    }

    public String findPw(String name, String userId){
        user targetUser = userRepository.findByUserId(userId);

        if (targetUser == null){
            return "error cant find user";
        }

        if (targetUser.getName() == name){
            return targetUser.getPw();
        }

        return "error user name is mismatch";
    }

}
