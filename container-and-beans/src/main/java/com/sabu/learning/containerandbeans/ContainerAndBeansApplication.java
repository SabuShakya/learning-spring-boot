package com.sabu.learning.containerandbeans;

import com.sabu.learning.containerandbeans.main.MyConfiguration;
import com.sabu.learning.containerandbeans.main.MyService;
import com.sabu.learning.containerandbeans.main.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class ContainerAndBeansApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContainerAndBeansApplication.class, args);

        // instantiate an application context by providing it configs
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);

        contextSimpleExample(context);
        testBeanScope(context);
        context.close();
    }

    private static void contextSimpleExample(AnnotationConfigApplicationContext context) {
        // get MyService bean(instance) from the context
        MyService myService1 = context.getBean(MyService.class);

        myService1.log("Hello from service1");

        MyService myService2 = context.getBean(MyService.class);
        System.out.println("Service1 HashCode=" + myService1.hashCode());
        System.out.println("Service2 HashCode=" + myService2.hashCode());
    }

    public static void testBeanScope(AnnotationConfigApplicationContext context) {
//        Person personA = (Person) context.getBean("personSingleton");
//        Person personB = (Person) context.getBean("personSingleton");
        Person personA = (Person) context.getBean("personPrototype");
        Person personB = (Person) context.getBean("personPrototype");

        personA.setName("Sabu");
        System.out.println("PersonA name equals personB name ? " + personA.getName().equals(personB.getName()));
    }

}
