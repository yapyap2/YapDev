package com.example.yapdev;

import lombok.Data;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor

@Data
public class threadTest extends Thread{

    private int num;
    boolean startSwitch = false;

    @Override
    public void run() {
        startSwitch = true;
        while(startSwitch){
            System.out.println("Thread is run,,,,,    : " + num);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
