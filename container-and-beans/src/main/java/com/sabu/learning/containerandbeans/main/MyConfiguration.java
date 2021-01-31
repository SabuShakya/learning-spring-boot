package com.sabu.learning.containerandbeans.main;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@ComponentScan(value = "com.sabu.learning.containerandbeans.main")
public class MyConfiguration {

//    @Bean
//    public MyService getService() {
//        return new MyService();
//    }

//    @Bean
//    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
//    public Person personSingleton(){
//        return new Person();
//    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Person personPrototype(){
        return new Person();
    }
}
