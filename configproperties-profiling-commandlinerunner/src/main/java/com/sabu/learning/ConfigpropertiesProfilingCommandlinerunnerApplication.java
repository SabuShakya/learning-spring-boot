package com.sabu.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ConfigpropertiesProfilingCommandlinerunnerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ConfigpropertiesProfilingCommandlinerunnerApplication.class, args);
        ApplicationConfigs applicationConfigs = context.getBean(ApplicationConfigs.class);
        System.out.println("Application Configs From====>"+applicationConfigs.getFrom());
        System.out.println("Application Configs HOST====>"+applicationConfigs.getHost());
        System.out.println("Application Configs USERNAME====>"+applicationConfigs.getSecurity().getUserName());
        System.out.println("Application Configs PASSWORD====>"+applicationConfigs.getSecurity().getPassword());
    }

}
