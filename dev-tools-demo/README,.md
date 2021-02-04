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

Next press double shift and search for Registry and select. Then look for *compiler.automake.allow.when.app.running* and enable it. 

