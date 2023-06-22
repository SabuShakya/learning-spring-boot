package com.sabu.learning.beanlifecycle;

import com.sabu.learning.beanlifecycle.beans.Config;
import com.sabu.learning.beanlifecycle.beans.HookExampleUsingInterfaces;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class BeanLifeCycleApplication {

    public static void main(String[] args) {
        // SpringApplication run will also return an application context.
        ApplicationContext context = SpringApplication.run(BeanLifeCycleApplication.class, args);

//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        HookExampleUsingInterfaces hookExampleUsingInterfaces = context.getBean(HookExampleUsingInterfaces.class);
        hookExampleUsingInterfaces.hookExampleUsingInterfaces();
//        context.close();
        ((ConfigurableApplicationContext) context).close();
    }

}
