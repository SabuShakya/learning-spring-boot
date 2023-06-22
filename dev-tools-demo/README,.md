# SPRING DEV TOOLS #

- Additional tools that help enhance development experience.
- Provides additional development-time features like automatic restart on code update,set property defaults,live reload.
- We need to add following dependency to our pom.xml

```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
    </dependency>
</dependencies>
```

If auto restart is not working in intellij, Enable Build Project Automatically from **File > Settings > Compiler >
Select Enable Build Automatically**.

Next press double shift and search for Registry and select. Then look for *compiler.automake.allow.when.app.running* and
enable it.

## [How Dev Tools work?](https://reflectoring.io/spring-boot-dev-tools/)##

Spring Boot Dev Tools hooks into the classloader of Spring Boot to provide a way to restart the application context
on-demand or to reload changed static files without a restart.

To do this, Spring Boot Dev Tools divides the application’s classpath into two classloaders:

- the base classloader contains rarely changing resources like the Spring Boot JARs or 3rd party libraries
- the restart classloader contains the files of our application, which are expected to change in our dev loop. The
  restart functionality of Spring Boot Dev Tools listens to changes to the files in our application and then throws away
  and restarts the restart classloader. This is faster than a full restart because only the classes of our application
  have to be reloaded.

