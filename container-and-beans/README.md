## IOC Container ##

It is a predefined program, or a component of Spring that implements IoC and DI. It injects dependencies into an object 
and make it ready for use.The interface ApplicationContext represents the Spring IoC container and is responsible for
instantiating,configuring and assembling beans.*The container receives instructions  on what objects to instantiate, 
configure and assemble by reading configuration metadata*. BeanFactory and ApplicationContext are two basis for Spring
Framework's IoC Container. 
Spring IoC container gets initialized when the application starts and, when a bean is requested the dependencies 
are injected automatically.

![Alt text](./ioccontainer.jpg?raw=true "Title")

### ApplicationContext ###

It is an interface that represents the Spring IoC container and manages beans. It us a sub-interface of BeanFactory and 
provides additional functionalities like resolving messages, supporting internationalization, publishing events, 
and application-layer specific contexts. Some of the useful ApplicationContext implementations are:

|Implementation Type |Description|
|:---|:---|
| AnnotationConfigApplicationContext | when using Spring in standalone Java applications and using annotations for configuration, we use this to initialize container and get bean objects.|
| AnnotationConfigWebApplicationContext | web based variant of AnnotationConfigApplicationContext |
| ClassPathXmlApplicationContext | when we have spring bean configuration xml file in standalone application,then we use this to load file from classpath and get container object.|
| FileSystemXmlApplicationContext| to load an XML-based Spring configuration file from the file system or from URLs. |
| XmlWebApplicationContext | when we use xml based configuration in web application |

Simple implementation of `ApplicationConfigApplicationContext`, using a Spring or Spring boot project.

Create a Simple MyService java class that has a simple log method.
```java
    package com.sabu.learning.containerandbeans.main;

    import java.util.Date;

    public class MyService {

	public void log(String msg){
		System.out.println(new Date()+"::"+msg);
	}
}
```

Create a configuration Class to configure bean of MyService class.
```java
package com.sabu.learning.containerandbeans.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.sabu.learning.containerandbeans.main")
public class MyConfiguration {

    @Bean
    public MyService getService() {
        return new MyService();
    }
}

```
Here `@Configuration` implies the class declares one or more @Bean methods and may be processed by Spring container to 
generate bean definition and services request for those beans at runtime.
`@ComponentScan` configures which packages to scan for classes with annotations.
We have added a method that returns an instance of MyService and annotated with @Bean. This will allow the container to 
create a bean object of MyService and provide it later in the application when it's dependency is required.

```java
    package com.sabu.learning.containerandbeans.main;

    import org.springframework.context.annotation.AnnotationConfigApplicationContext;

    public class MainClass {
        public static void main(String[] args) {
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
```

When we run above code, we will get the following output:
```text
    Hello from service1::Fri Jan 29 12:50:47 NPT 2021
    Service1 HashCode=795594631
    Service2 HashCode=795594631
```
We have used MyService bean twice in our code. Both times we get the same instance of MyService, as we see from their 
hashcode. By default, the scope of bean is defined to be singleton and hence we get the same instance. (Look below for Bean Scopes.)

In above example instead of creating @Bean annotated method in Configuration class, we can annotate MyService with 
@Component annotation and yet the code will produce the same result. This is because we have configured our container to
scan for components inside the package "com.sabu.learning.containerandbeans.main". Our MyService class is located 
inside the same package and is annotated with @Component annotation, which implies that it's a bean and is to be added 
to the context.

```java
package com.sabu.learning.containerandbeans.main;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyService {

    public void log(String msg) {
        System.out.println(msg + "::" + new Date());
    }
}
```

```java
package com.sabu.learning.containerandbeans.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.sabu.learning.containerandbeans.main")
public class MyConfiguration {

//    @Bean
//    public MyService getService() {
//        return new MyService();
//    }
}

```
References: 
   -[x] [Spring IoC, Spring Bean Example Tutorial](https://www.journaldev.com/2461/spring-ioc-bean-example-tutorial)
   -[x] [The Spring ApplicationContext](https://www.baeldung.com/spring-application-context)

### Beans ###

Beans are simply the objects that are instantiated, assembled, managed by Spring IoC container and form backbone 
of an application. Any normal Java POJO class can be a Spring Bean if it's configured to be initialized via container 
by providing configuration metadata. 



