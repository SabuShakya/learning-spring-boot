package com.sabu.learning.devtoolsdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TestRestController {

    @GetMapping("/")
    public String sayHello(){
        System.out.println("Testing dev tools say hello");
        return "Hello! Time is "+ LocalDateTime.now();
    }

    @GetMapping("/workout")
    public String getWorkout(){
        return "Learn Spring Boot devtools!";
    }

    @GetMapping("/test")
    public String testDevTools(){
        System.out.println("testing devtools");
        return "Testing devtools";
    }

    @GetMapping("/oneMore")
    public String oneMoreTest(){
        return "Running one more after enabling build automatic and registry :" +
                "compiler.automake.allow.when.app.running!";
    }
}
