package com.sabu.learning.containerandbeans.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScopesController {

    @Autowired
    HelloMessageGenerator requestedScopeBean;

    @RequestMapping("/scopes/request")
    public String getRequestScopeMessage() {
        String message1 = requestedScopeBean.getMessage();
        System.out.println(message1);
        requestedScopeBean.setMessage("Hi Bean");
        String message2 = requestedScopeBean.getMessage();
        System.out.println(message2);
        return "scopesExample";
    }
}
