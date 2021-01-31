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

### Bean Scopes ###

The scope of a bean defines the lifecycle and visibility of that bean in the contexts in which it is used. There are six 
types of scopes according to the latest versions of Spring framework.

1. [Singleton](#singleton)
2. [Prototype](#prototype)
3. [Request](#request)
4. [Session](#session)
5. [Application](#application)
6. [WebSocket](#websocket)

#### Singleton ####
The container will create only one instance of the bean, and all requests to that bean will return the same object, 
which is cached. Any change to the object will be reflected in all of it's references. If no scope is specified, 
it is the default value. 

let's create a class Person.
```java
package com.sabu.learning.containerandbeans.main;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {
    private String name;
}
```
Then create it's singleton bean.
```java
    package com.sabu.learning.containerandbeans.main;
    
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.context.annotation.Scope;

    @Configuration
    @ComponentScan(value = "com.sabu.learning.containerandbeans.main")
    public class MyConfiguration {

    @Bean
    @Scope("singleton")
    public Person personSingleton(){
        return new Person();
    }
}
```
We can also use a constant instead of string value as:
```java
    package com.sabu.learning.containerandbeans.main;
    
    import org.springframework.beans.factory.config.ConfigurableBeanFactory;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.context.annotation.Scope;

    @Configuration
    @ComponentScan(value = "com.sabu.learning.containerandbeans.main")
    public class MyConfiguration {

   @Bean
   @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
   public Person personSingleton(){
       return new Person();
   }
}
```
Next we write a simple code in our main method to see if the objects referring to the Person bean have same values.

```java
    public static void main(String[] args) {
      
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);

        Person personA = (Person) context.getBean("personSingleton");
        Person personB = (Person) context.getBean("personSingleton");
       
        personA.setName("Sabu");
        System.out.println("PersonA name equals personB name ? " + personA.getName().equals(personB.getName())); // outputs true
        context.close();
    }
```

We get output as:
```
PersonA name equals personB name ? true

```
#### Prototype ####

The container will return a new instance of the bean everytime the bean is requested.
Let's change the scope of Person to `@Scope("prototype")` or to `@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)`.

```java
    package com.sabu.learning.containerandbeans.main;

    import org.springframework.beans.factory.config.ConfigurableBeanFactory;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.context.annotation.Scope;

    @Configuration
    @ComponentScan(value = "com.sabu.learning.containerandbeans.main")
    public class MyConfiguration {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Person personSingleton(){
        return new Person();
    }
}
```
And in our main method, change the bean name from "personSingleton" to "personPrototype".
```
    Person personA = (Person) context.getBean("personPrototype");
    Person personB = (Person) context.getBean("personPrototype");
```
Now, if we rerun our main method we get the output as:
```
PersonA name equals personB name ? false
```
 
The scopes `request`, `session`, `application`, and `WebSocket` are available only in a web-aware application context 
and often used less in practice.

#### Request ####
Creates a new instance for each Http request.

Let's create a simple HelloMessageGenerator class.
```java
package com.sabu.learning.containerandbeans.main;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
/* proxyMode = ScopedProxyMode.TARGET_CLASS is necessary because at the time of instantiation of web application context
   there is no request. So Spring will create a proxy to be injected as a dependency and instantiate a target bean
   when it is needed in a request.
 */
// @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS) or,
@RequestScope // shortcut for above
public class HelloMessageGenerator {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

```
Next, create a ScopesController and inject the HelloMessageGenerator bean to it.

```java
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
```
Each time the request is run, we see in console that the message value is reset to null, even though we changed it later
in method. This is because a different instance is returned for each request.

#### Session ####
Creates an instance for each Http session.
In above example, let's change the scope of HelloMessageGenerator to `@SessionScope`.

```java
@SessionScope
public class HelloMessageGenerator {
    
}
```
If we now rerun the application and make a request, for the first time we will see the message value to be null and then set to
'Hi Bean'. Now if we again make request, we will see that 'Hi Bean' is logged twice. This is because the same
instance of bean is returned for the entire session.

#### Application ####
Creates bean instance for the lifecycle of a ServletContext.

#### WebSocket ####
Creates a particular WebSocket session.

Reference : [Quick Guide to Spring Bean Scopes](https://www.baeldung.com/spring-bean-scopes)