package com.sabu.learning.containerandbeans;

import com.sabu.learning.containerandbeans.main.MyConfiguration;
import com.sabu.learning.containerandbeans.main.MyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class ContainerAndBeansApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContainerAndBeansApplication.class, args);

        // instantiate an application context by providing it configs
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);

        // get MyService bean(instance) from the context
        MyService myService1 = context.getBean(MyService.class);

        myService1.log("Hello from service1");

        MyService myService2 = context.getBean(MyService.class);
        System.out.println("Service1 HashCode=" + myService1.hashCode());
        System.out.println("Service2 HashCode=" + myService2.hashCode());

        context.close();
    }

}
