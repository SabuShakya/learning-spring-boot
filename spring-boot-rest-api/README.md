# Spring Boot Fundamentals #

## Spring Boot Starters ##

Spring boot starters are a set of convenient dependency descriptors which can be easily included in any level of
application. These work as a bootstrapping process for Spring related technologies. All the dependencies will be managed
by Spring Boot Starters. We need to just include the correct starter in our application and Spring boot will ensure all
dependenciies required for the chosen starter are in your classpath.

```
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Above dependency in pom.xml will ensure that all the required dependencies should be in the classpath and we are set to
work on our web application. There are about 50+ starters offered by Spring Boot. One of them is the Web starter,shown
above. It ensures all dependencies to create Spring Web application(including REST) are included in classpath, and will
also add tomcat-server as default server to run web application. Reference :

- [Spring Boot Starters](https://www.javadevjournal.com/spring/spring-boot-starters/)

## Auto-Configuration ##

Spring Boot attempts to automatically configure Spring Application based on JAR dependencies added. To opt-in
auto-configuration we can add `@EnableAutoConfiguration` or `@SpringBootApplication` annotations to one of our
configuration(@Configuration) class. Either one of the annotation should only be added. We can provide various
attributes like `exclude`,`excludeName` to the above annotations to disable configurations of specific config classes.

## @SpringBootApplication Annotation ##

This annotation is equivalent to the combination of *@EnableAutoConfiguration*, *@ComponentScan* and *@Configuration*.

- @EnableAutoConfiguration : enables Spring Boot's auto configuration.
- @ComponentScan : enables @Component scan on the package where application is located.
- @Configuration : allows to register extra beans or import addition configuration classes.

```java
package com.sabu.learning.springbootrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApiApplication.class, args);
	}

}
```

This annotation is not compulsory and be replaced by any of the features that enables it.

```java
package com.example.myapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@EnableAutoConfiguration
@Import({ MyConfig.class, MyAnotherConfig.class })
public class SpringBootRestApiApplication {

    public static void main(String[] args) {
            SpringApplication.run(SpringBootRestApiApplication.class, args);
    }

}
```

## Spring Boot Initialization Steps ##

![Alt text](./springInitialization.png?raw=true "Title")

[Here for Detail Explanation](https://stackoverflow.com/a/39214547/11709663)

# Spring REST API Annotations #

## @RestController ##

-> Combination of @Controller and @ResponseBody annotations. -> Eliminates the need of annotating each method with
@ResponseBody. -> indicates that the data returned by each method will be written straight into the response body
instead of rendering a template.

## @RequestBody ##

-> If any method parameter is annotated with @RequestBody, Spring will bind the incoming HTTP request body to that
property. -> Behind the scenes, Spring will use HTTP message converters to convert the HTTP request body into domain
object
[deserialize request body to domain object], based on Accept or Content-Type header present in request.

## @ResponseBody ##

-> If any method is annotated with it, the return value will be binded to an outgoing HTTP response body. -> Here also,
Spring will behind the scenes, use HTTP message converters to convert the return value to HTTP response
body[serialize the object to response body] based on Content-Type header in HTTP request.

## Response Entity ##

-> It represents the entire HTTP response. We can specify status code, headers and body that goes into it.

-> It comes with several constructors to carry the information we want to send in an HTTP response.

-> https://www.baeldung.com/spring-response-entity

## @PathVariable ## 

-> This annotation indicates that a method parameter should be bound to a URI template variable [the one in ‘{}’].

