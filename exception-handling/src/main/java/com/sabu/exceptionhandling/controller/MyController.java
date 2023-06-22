package com.sabu.exceptionhandling.controller;


import com.sabu.exceptionhandling.exception.MyCustomException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/test")
    public String testException() {
        throw new MyCustomException("This is a custom exception as bad request.");
    }
}
