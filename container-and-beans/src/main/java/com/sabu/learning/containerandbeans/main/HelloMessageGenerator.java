package com.sabu.learning.containerandbeans.main;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Component
// @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    /*  proxyMode = ScopedProxyMode.TARGET_CLASS is necessary because at the time of instantiation of web application context
       there is no request. So Spring will create a proxy to be injected as a dependency and instantiate a target bean
       when it is needed in a request.*/
//@RequestScope // shortcut for above
@SessionScope
public class HelloMessageGenerator {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
