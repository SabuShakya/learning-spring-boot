package com.sabu.learning.containerandbeans.main;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyService {

    public void log(String msg) {
        System.out.println(msg + "::" + new Date());
    }
}
